package com.ocean.lmh.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.MenuValue;
import com.ocean.lmh.base.model.entity.system.UserValue;
import com.ocean.lmh.base.model.request.system.MenuReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;

/**
 * 系统菜单管理的业务逻辑类，负责系统菜单相关的各种操作。
 * @author liminghui
 *
 */
@Transactional
@Service("menuService")
public class MenuService {

	private static Logger log = LoggerFactory.getLogger(MenuService.class);

	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	@Resource(name = "mencached")
	private MemcachedHelper memcachedHelper;

	/**
	 * 根据条件分页获取菜单列表信息
	 * @param menuReq 条件实体类，包含条件[name,description]
	 * @return 菜单结果集
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PaginationBean<MenuValue> menuList(MenuReq menuReq) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String str = menuReq.getKeyword();
		if (AssertHelper.isNotEmptyString(str)) {
			map.put("name", str);
			map.put("description", str);
		}		
		PaginationBean<UserValue> pb = new PaginationBean<UserValue>(menuReq.getCurrent(), 0, menuReq.getPageSize());
        map.put("page", pb);
		return this.baseDao.queryForListPageByMap(
				SqlKeyConstants.MENU_SELECT_BYPAGE, map);
	}

	/**
	 * 添加一颗菜单树
	 * @param menu 待添加的菜单实体
	 * @return 如果已存在了相同的菜单树名则返回true，否则返回false
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addMenuTree(MenuValue menu) 
	{
		if (existsByTreeName(menu.getName())) 
		{
			return true;
		}
		
		//0为菜单树的默认起始位置
		menu.setPosition(0);
		add(menu);
		return false;
	}

	/**
	 * 根据菜单树名和菜单id为null来判断记录是否存在
	 * @param name 菜单树名
	 * @return 存在返回true，不存在返回false
	 */
	private boolean existsByTreeName(String name) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		List<MenuValue> list = this.baseDao.queryForList(SqlKeyConstants.MENU_SELECT_BYNAMEANDPID, map);
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			return true;
		}
		return false;
	}

	/**
	 * 添加一项菜单项
	 * @param menu 待添加的菜单实体
	 * @return 如果存在相同的菜单项名返回true，否则返回false
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addMenuItem(MenuValue menu) 
	{
		List<MenuValue> list = existsInMenus(menu);
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			return true;
		}
		MenuValue pmenu = menu.getParent();
		if (pmenu != null) 
		{
			menu.setPid(pmenu.getId());
		}
		add(menu);
		return false;
	}

	/**
	 * 根据id删除菜单记录，此操作会将其所有子菜单一起删除
	 * @param id 菜单项id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void removeMenu(Integer id) 
	{
		//先删除此菜单下的所有子菜单
		this.baseDao.deleteData(SqlKeyConstants.MENU_DELETE_BYPARENTID, id);
		
		this.baseDao.deleteData(SqlKeyConstants.MENU_DELETE_BYPRIMARYKEY, id);
		
		//将菜单信息更新到缓存中
		setMenuInfoToMemcached();
	}

	/**
	 * 移动菜单
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void move(Integer id, Integer pid, Integer prevId, Integer nextId) 
	{
		if (log.isDebugEnabled()) 
		{
			log.debug(id + "," + pid + "," + prevId + "," + nextId);
		}

		MenuValue menu = getMenuById(id);
		MenuValue parent = getMenuTree(pid);//getMenuById(pid);
		if (prevId != null && nextId != null ) 
		{
			// 放到中间
			MenuValue prev = getMenuById(prevId);
			int prevPosition = 0;
			for (MenuValue child : parent.getChildren()) 
			{
				child.setPosition(2 * child.getPosition());
				if (child.getId() == prev.getId()) 
				{
					prevPosition = child.getPosition();
				}
				this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, child);
			}
			menu.setPosition(prevPosition + 1);
			menu.setPid(parent.getId());
			this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);
		} else if (prevId == null && nextId == null) 
		{
			// 放到节点下
			int i = 1;
			i = updateMenuPosition(id,parent,i);
			menu.setPosition(i);
			menu.setPid(parent.getId());
			this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);
		} else if (prevId == null) 
		{
			// 放到最前面
			menu.setPosition(1);
			menu.setPid(parent.getId());
			this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);
			int i = 2;
			updateMenuPosition(id,parent,i);
		} else if (nextId == null) 
		{
			// 放到最后面
			int i = 1;
			i = updateMenuPosition(id,parent,i);
			menu.setPosition(i);
			menu.setPid(parent.getId());
			this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);
		}
		
		//将菜单信息更新到缓存中
		setMenuInfoToMemcached();
	}
	private Integer updateMenuPosition(Integer menuId, MenuValue parentMenu, Integer positionValue)
	{
		for (MenuValue child : parentMenu.getChildren()) 
		{
			if (child.getId() != menuId) 
			{
				child.setPosition(positionValue++);
				this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, child);
			}
		}
		return positionValue;
	}

	/**
	 * 根据菜单id获取一项菜单记录信息
	 * @param id 菜单项id
	 * @return map对象包含数据[id:xx,pid:xx,name:xx,uri:xx]
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, Object> getMenuHasPidById(Integer id) 
	{
		MenuValue menu = this.baseDao.query(SqlKeyConstants.MENU_SELECT_BYPRIMARYKEY, id);
		MenuValue par_menu = this.baseDao.query(SqlKeyConstants.MENU_SELECT_BYPRIMARYKEY, menu.getPid());
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("id", menu.getId());
		resMap.put("pid", par_menu.getId() );
		resMap.put("name", menu.getName());
		resMap.put("uri", menu.getUri());
		return resMap;
	}

	/**
	 * 更新菜单项记录信息
	 * @param menu 待更新的菜单项
	 * @return 如果存在相同的菜单项名则返回true，否则返回false
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean updateMenuItem(MenuValue menu) 
	{
		List<MenuValue> list = existsInMenus(menu);
		
		//判断是否存在相同的节点名称
		if (AssertHelper.isNotEmptyCollection(list)) 
		{
			return true;
		}		
		
		menu.setPid(null);
		menu.setPosition(null);
		menu.setDescription(null);
		this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);
		
		//将菜单信息更新到缓存中
		setMenuInfoToMemcached();
		return false;
	}

	/**
	 * 更新菜单树的描述
	 * @param id 菜单树id
	 * @param description 新的菜单树描述信息
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateMenuDescription(Integer id, String description) 
	{
		MenuValue menu = new MenuValue();
		menu.setId(id);
		menu.setDescription(description);
		this.baseDao.updateData(SqlKeyConstants.MENU_UPDATE_MENU, menu);//menuDao.updateDescription(id, description);
	}

	/**
	 * 根据id递归获取菜单项信息，以此id的菜单为根菜单及其下所有子菜单
	 * @param id 菜单项id
	 * @return 菜单信息（包含所有的子菜单）
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public MenuValue getMenuTree(Integer id) 
	{
		return getMenuAndChildById(id);
	}

	/**
	 * 获取所有的根菜单树记录信息
	 * @return 根菜单结果集
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<MenuValue> getAllRootTree() 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pid", null);
		return this.baseDao.queryForList(SqlKeyConstants.MENU_SELECT_ROOTMENUNODE, map);
	}

	/**
	 * 根据菜单id获取菜单记录信息
	 * @param menuid 菜单id
	 * @return 菜单记录
	 */
	public MenuValue getMenuById(Integer menuid)
	{
		return this.baseDao.query(SqlKeyConstants.MENU_SELECT_BYPRIMARYKEY, menuid);
	}
	
	/**
	 * 根据给定的菜单id获取菜单记录及其子菜单记录
	 * @param menuid 菜单id	
	 * @return 菜单记录
	 */
	public MenuValue getMenuAndChildById(Integer menuid)
	{
		return this.baseDao.query(SqlKeyConstants.MENU_SELECT_MENUHASCHILD, menuid);
	}
	
	/**
	 * 添加一项菜单,作为菜单树和菜单项的实际添加方法
	 * @param menu 待添加的菜单实体
	 */
	public void add(MenuValue menu)
	{
		MenuValue pmenu = this.baseDao.query(SqlKeyConstants.MENU_SELECT_BYPRIMARYKEY, menu.getPid());
		if (pmenu != null) 
		{
			Integer position = getMaxPositionId(pmenu.getId()); 
			menu.setPosition(position==null?0:position+1);
		}
		this.baseDao.insertData(SqlKeyConstants.MENU_INSERT_MENU, menu);
		
		//将菜单信息更新到缓存中
		setMenuInfoToMemcached();
	}
	
	/**
	 * 获取id为menuid的菜单的已有最大位置
	 * @param menuid 菜单id
	 * @return 最大位置数
	 */
	public Integer getMaxPositionId(Integer menuid)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Pid", menuid);
		return this.baseDao.query(SqlKeyConstants.MENU_SELECT_MAXPOSITION, map);
	}
	
	/**
	 * 获取指定菜单名和id不为传入id值的菜单记录
	 * @param menu 菜单实体
	 * @return 查询结果集
	 */
	public List<MenuValue> existsInMenus(MenuValue menu) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", menu.getName());
		if (menu.getParent()!=null) 
		{
			map.put("id", menu.getId());
		}		
		return this.baseDao.queryForList(SqlKeyConstants.MENU_SELECT_BYNAMEANDNOTID, map);
	}
	
	/**
	 * 当添加、更新和删除系统参数时从新将修改后的参数放入缓存中
	 */
	public void setMenuInfoToMemcached()
	{
		List<MenuValue> allTree = getAllRootTree();
		for (MenuValue tree : allTree) 
		{
			tree = getMenuTree(tree.getId());
			memcachedHelper.setValueToMemcache(tree.getName(), 0, tree);
		}
	}
}
