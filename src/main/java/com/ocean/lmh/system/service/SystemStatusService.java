package com.ocean.lmh.system.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.SystemStatusValue;

/**
 * 系统状态管理的业务逻辑类，主要提供从业务逻辑层获取数据库中
 * 预订好的系统状态信息的查询方法。
 * @author liminghui
 *
 */
@Service("systemStatusService")
@Transactional
public class SystemStatusService {
	
	private static Logger log	= LoggerFactory.getLogger(SystemStatusService.class);
	
	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	/**
	 * 根据系统状态级别获取系统中符合要求的所有状态记录信息
	 * @param level 系统状态级别
	 * @return 状态记录集合
	 */
	public List<SystemStatusValue> getSystemStatusListByLevel(Integer level)
	{
		log.debug("system status level :: "+level);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusLevel", level);
		return this.baseDao.queryForList(SqlKeyConstants.SYSTEMSTATUS_SELECT_BYLEVEL, map);
	}

	/**
	 * 根据父系统状态id获取对应的子状态表中符合条件的所有子状态记录的id值
	 * @param statusId 父状态id
	 * @return 子状态statusid记录集合
	 */
	public List<Integer> getSystemStatusItemsByStatusId(Integer statusId)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("statusId", statusId);
		return this.baseDao.queryForList(SqlKeyConstants.SYSTEMSTATUSITEM_SELECT_BYPARENTSTATUSID, map);
	}
}
