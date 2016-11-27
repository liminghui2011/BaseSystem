package com.ocean.lmh.system.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.cache.MemcachedHelper;
import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.model.entity.system.MenuValue;
import com.ocean.lmh.base.model.entity.system.SystemStatusValue;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.base.util.NumberUtils;
import com.ocean.lmh.system.model.vo.LogConfigParamValue;
import com.ocean.lmh.system.model.vo.LogConfigValue;

/**
 * 获取系统公用数据的业务类，主要提供系统初始化时需要的那些公用数据的方法
 * 包括菜单树、系统参数、省份、城市等
 * @author liminghui
 *
 */
@Service("globalResourceService")
public class GlobalResourceService {

	private Logger log = LoggerFactory.getLogger(GlobalResourceService.class);

	@Autowired
	private Properties systemstatus;
	
	@Resource(name = "provinceJsonResource")
	private ClassPathResource provinceJsonResource;

	@Resource(name = "cityJsonResource")
	private ClassPathResource cityJsonResource;

	@Resource(name = "logConfigXmlResource")
	private ClassPathResource logConfigXmlResource;

	@Resource(name = "menuService")
	private MenuService menuService;

	@Resource(name = "privilegeService")
	private PrivilegeService privilegeService;
	
	@Resource(name = "operPrivilegeService")
	private OperPrivilegeService operPrivilegeService;

	@Resource(name = "paramService")
	private ParamService paramService;
	
	@Resource(name = "systemStatusService")
	private SystemStatusService systemStatusService;

	@Resource(name = "mencached")
    private MemcachedHelper memcachedHelper;

	/**
	 * 保存系统参数到缓存中
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, Map<String, String>> loadParameter() 
	{
		Map<String, Map<String, String>> paramMap = paramService.getAllParams();
		setParamsToMemcache(AppConstant.PARAMETER_MAP, 0, paramMap);
		return paramMap;
	}

	/**
	 * 保存省份信息到application范围中
	 * @throws IOException 获取输入流失败。
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> loadProvince() throws IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		InputStream in = provinceJsonResource.getInputStream();
		Map<String, List<Map<String, String>>> provinceMap = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			provinceMap = mapper
					.readValue(
							in,
							new TypeReference<Map<String, List<Map<String, String>>>>() {
							});
			List<Map<String, String>> provinces = provinceMap.get("province");
			for (Map<String, String> province : provinces) 
			{
				map.put(province.get("code"), province.get("name"));
			}
		}
		catch (IOException e) 
		{
			log.error("读取[" + provinceJsonResource.getPath() + "] 失败", e);
			throw e;
		} 
		finally 
		{
			in.close();
		}		
		setParamsToMemcache(AppConstant.PROVINCE_MAP, 0, map);
		return map;
	}

	/**
	 * 保存地市信息到application范围中
	 * @throws IOException 获取输入流失败
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> loadCity() throws IOException 
	{
		ObjectMapper mapper = new ObjectMapper();
		InputStream in = cityJsonResource.getInputStream();
		Map<String, List<Map<String, String>>> cityMap = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> map = new HashMap<String, String>();
		try 
		{
			cityMap = mapper
					.readValue(
							in,
							new TypeReference<Map<String, List<Map<String, String>>>>() {
							});
			List<Map<String, String>> cities = cityMap.get("city");
			for (Map<String, String> city : cities) 
			{
				map.put(city.get("code"), city.get("name"));
			}
		} 
		catch (IOException e) 
		{
			log.error("读取[" + cityJsonResource.getPath() + "] 失败", e);
			throw e;
		}
		finally 
		{
			in.close();
		}		
		setParamsToMemcache(AppConstant.CITY_MAP, 0, map);
		return map;
	}

	/**
	 * 加载所有菜单树
	 */
	public void loadAllTree() 
	{
		List<MenuValue> allTree = menuService.getAllRootTree();
		for (MenuValue tree : allTree) 
		{
			tree = menuService.getMenuTree(tree.getId());
			memcachedHelper.setValueToMemcache(tree.getName(), 0, tree);
		}
	}

	/**
	 * 加载系统所有权限URI
	 */
	public List<String> loadPrivilegeUriList() 
	{
		List<String> privilegeUriList = privilegeService.getPrivilegeUriList();
		List<String> operPrivilegeUri = operPrivilegeService.getOperPrivilegeUriList();
		privilegeUriList.addAll(operPrivilegeUri);
		setParamsToMemcache(AppConstant.PRIVILEGE_URI_LIST, 0, privilegeUriList);
		return privilegeUriList;
	}


	/**
	 * 读取user-action-log.xml配置文件
	 * 里面可以配置请求路径、请求编号、请求参数、请求类型（get/post）等
	 * @throws IOException 获取文件流失败
	 * @throws DocumentException 读取xml文件异常
	 */
	public Map<String, LogConfigValue> loadLogConfigValue() throws IOException,DocumentException 
	{

		InputStream inputStream = logConfigXmlResource.getInputStream();

		//解析xml文件
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		Element root = document.getRootElement();

		Iterator<?> actionIter = root.elementIterator();
		Map<String, LogConfigValue> configValues = new HashMap<String, LogConfigValue>();

		while (actionIter.hasNext()) {
			Element actionElement = (Element) actionIter.next();

			LogConfigValue userActionValue = new LogConfigValue();

			String url = actionElement.attributeValue("url");
			String method = actionElement.attributeValue("method");

			userActionValue.setUrl(url);
			userActionValue.setActionCode(actionElement
					.attributeValue("action-code"));
			userActionValue.setDesc(actionElement.attributeValue("desc"));
			userActionValue.setMethod(method);

			//获取参数列表
			Iterator<?> paramIter = actionElement.elementIterator();
			List<LogConfigParamValue> paramList = new ArrayList<LogConfigParamValue>();
			while (paramIter.hasNext()) 
			{
				LogConfigParamValue logParamValue = new LogConfigParamValue();
				Element paramElement = (Element) paramIter.next();

				String name = paramElement.attributeValue("name");

				if (AssertHelper.isNotEmptyString(name)) 
				{
					logParamValue.setName(name);
				}

				String type = paramElement.attributeValue("column");
				if (AssertHelper.isNotEmptyString(type)) 
				{
					logParamValue.setColumn(type);
				}

				String maxLength = paramElement.attributeValue("maxLength");
				if (AssertHelper.isNotEmptyString(maxLength)) 
				{
					logParamValue.setMaxLength(0);
				} 
				else 
				{
					logParamValue.setMaxLength(NumberUtils.parseInt(maxLength));
				}

				paramList.add(logParamValue);
			}

			userActionValue.setParamNames(paramList);

			String key = url;
			if (AssertHelper.isNotEmptyString(method)) 
			{
				key = method.toUpperCase() + url;
			}
			configValues.put(key, userActionValue);
		}		
		setParamsToMemcache(AppConstant.LOG_CONFIG_VALUE, 0, configValues);
		return configValues;
	}

	/**
	 * 加载系统预订的各种系统状态参数值，用来判断从某一个状态是否可以转换到另一个状态
	 */
	public Map<Integer, List<Integer>> loadSystemStatus()
	{
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		String message = (String) systemstatus.get("system.status.level");
		if (AssertHelper.isNotEmptyString(message)) 
		{
			Integer level = Integer.valueOf(message);
			List<SystemStatusValue> statusList = systemStatusService.getSystemStatusListByLevel(level);
			for (SystemStatusValue status : statusList) {
				Integer statusId = status.getStatusId();
				List<Integer> statusIds = systemStatusService.getSystemStatusItemsByStatusId(statusId);
				map.put(statusId, statusIds);
			}
		}		
		setParamsToMemcache(AppConstant.SYSTEM_STATUS_MAP, 0, map);
		return map;
	}

	/**
	 * 将值以key为键object为值添加缓存中去
	 * @param key 键
	 * @param time 在缓存储存的时长
	 * @param object 值
	 */
	public void setParamsToMemcache(String key, int time, Object object) 
	{
		memcachedHelper.setValueToMemcache(key, time, object);
	}
	
}
