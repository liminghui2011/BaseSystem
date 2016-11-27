package com.ocean.lmh.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.ParamItemValue;
import com.ocean.lmh.base.model.entity.system.ParamValue;
import com.ocean.lmh.base.model.request.system.ParamReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.AssertHelper;
/**
 * 系统参数管理业务类，主要是对系统中的参数进行管理，包括添加、删除、修改、查询等操作
 * @author liminghui
 * @since 2013-08-28
 *
 */
@Transactional
@Service("paramService")
public class ParamService {
	
    @Resource(name = "mybatisBaseDao")
    private BaseDaoInterface baseDao;

    @Resource(name = "mencached")
	private MemcachedHelper memcachedHelper;
    

	/**
	 * 根据条件分页查询系统参数列表信息
	 * @param paramReq 封装查询条件的实体包含[name,description]
	 * @return 封装结果集的实体类
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PaginationBean<ParamValue> getParamList(ParamReq paramReq) 
	{
	    Map<String, Object> map = new HashMap<String, Object>();  
	    if(AssertHelper.isNotEmptyString(paramReq.getName()))
	    {
	        map.put("name", "%"+paramReq.getName()+"%");
	    }
	    if(AssertHelper.isNotEmptyString(paramReq.getDescription()))
        {
	        map.put("description", "%"+paramReq.getDescription()+"%");
        }
	    
	    PaginationBean<ParamValue> pb = new PaginationBean<ParamValue>(paramReq.getCurrent(), 0, paramReq.getPageSize());
	    map.put("page", pb);
	    return this.baseDao.queryForListPageByMap(SqlKeyConstants.PARAM_SELECT_PAGE, map); 
	}

	/**
	 * 根据id获取系统参数记录信息
	 * @param id 参数id
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ParamValue getParamById(Integer id) 
	{
	    return this.baseDao.query(SqlKeyConstants.PARAM_SELECT_BYID, id);
	}
	
	/**
	 * 添加一个系统参数信息
	 * @param param 待添加的参数实体
	 * @param result 结果实体
	 * @param request 请求对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void add(ParamValue param, BindingResult result, HttpServletRequest request) 
	{
		if (checkParams(param, result)) 
		{
			return;
		}

		if (existsByName(param.getName(), param.getId())) {
			result.rejectValue("name", "param.name.exists");
			return;
		}

		this.baseDao.insertData(SqlKeyConstants.PARAM_INSERT, param);
		int paramId = param.getId();
		for(ParamItemValue item : param.getItems())
		{
		    item.setParamId(paramId);
		    this.baseDao.insertData(SqlKeyConstants.PARAMITEM_INSERT, item);
		}
		setParametersToMemcached();
	}

	/**
	 * 更新一个系统参数信息
	 * @param param 待更新的参数实体
	 * @param result 结果实体
	 * @param request 请求对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(ParamValue param, BindingResult result, HttpServletRequest request) 
	{
		Log.debug("params::"+param.toString());
		if (checkParams(param, result)) 
		{
			return;
		}

		if (existsByName(param.getName(), param.getId())) 
		{
			result.rejectValue("name", "param.name.exists");
			return;
		}
		this.baseDao.updateData(SqlKeyConstants.PARAM_UPDATE, param);
		for(ParamItemValue item : param.getItems())
        {
            if(null != item.getId())
            {
                this.baseDao.updateData(SqlKeyConstants.PARAMITEM_UPDATE, item); 
            }
            else
            {
                item.setParamId(param.getId());
                this.baseDao.insertData(SqlKeyConstants.PARAMITEM_INSERT, item);
            }
           
        }
		setParametersToMemcached();
	}

	/**
	 * 根据id删除参数记录信息
	 * @param id 参数id
	 * @param request 请求对象
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void remove(Integer id, HttpServletRequest request) 
	{	
	    this.baseDao.deleteData(SqlKeyConstants.PARAMITEM_DELETE_BYPARAMID, id);
		this.baseDao.deleteData(SqlKeyConstants.PARAM_DELETE_BYID, id);
		setParametersToMemcached();
	}

	/**
	 * 检查子参数记录的key是否为空
	 * @param param 参数实体
	 * @param result 结果对象
	 * @return 如果存在key为空则返回true，否则返回false
	 */
	private boolean checkParams(ParamValue param, BindingResult result) 
	{
		List<ParamItemValue> itemList = param.getItems();
		for (int i=1; i<itemList.size(); i++) 
		{
			ParamItemValue item = itemList.get(i);
			if (AssertHelper.isEmptyString(item.getName())) 
			{
				result.rejectValue("items[" + i + "].name", "param.itemname.required");
				return true;
			}
			if (AssertHelper.isEmptyString(item.getValue())) 
			{
				result.rejectValue("items[" + i + "].value", "param.itemvalue.required");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据参数名称和参数id不为传入id判断参数是否已经存在了
	 * @param name 参数名称
	 * @param id 参数id
	 * @return 存在返回true，不存在返回false
	 */
	public boolean existsByName(String name,Integer id)
	{   Map<String, Object> map = new HashMap<String, Object>();
	    map.put("id", id);
	    map.put("name", name);
	    List<ParamValue> list = this.baseDao.queryForList(SqlKeyConstants.PARAM_SELECT_BYNAMEANDID, map);
	    if (AssertHelper.isNotEmptyCollection(list)) {
			return true;
		}
	    return false;
	}
	
	/**
	 * 根据详细参数id删除参数记录信息
	 * @param id 详细参数id
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void removeParamItem(Integer id)
	{
	    this.baseDao.deleteData(SqlKeyConstants.PARAMITEM_DELETEBYID, id);
	    
	}

	/**
	 * 获取所有的系统参数记录信息
	 * @return 以参数id为key，详细子参数map为值的map对象
	 */
	public Map<String, Map<String, String>> getAllParams() {
		List<ParamValue> params = this.baseDao.queryForList(SqlKeyConstants.PARAM_SELECT_ALLPARAMS, 
				new HashMap<String, Object>());
		Map<String, Object> map = new HashMap<String, Object>();
		for (ParamValue param : params) 
		{
			map.put("paramId", param.getId());
			List<ParamItemValue> list = this.baseDao.queryForList(SqlKeyConstants.PARAMITEM_SELECT_BYPARAMID, map);
			param.setItems(list);
			map.clear();
		}
		Map<String, Map<String, String>> paramMap = new HashMap<String, Map<String, String>>();
		for (ParamValue param : params) 
		{
			Map<String, String> itemMap = new HashMap<String, String>();
			for (ParamItemValue item : param.getItems()) 
			{
				itemMap.put(item.getName(), item.getValue());
			}
			paramMap.put(param.getName(), itemMap);
		}
		return paramMap;
	}
	
	/**
	 * 当添加、更新和删除系统参数时从新将修改后的参数放入缓存中
	 */
	public void setParametersToMemcached()
	{
		Map<String, Map<String, String>> paramMap = getAllParams();
		memcachedHelper.setValueToMemcache(AppConstant.PARAMETER_MAP, 0, paramMap);
	}

}
