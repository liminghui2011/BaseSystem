package com.ocean.lmh.system.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.ActionLogDetailValue;
import com.ocean.lmh.base.model.entity.system.ActionLogValue;
import com.ocean.lmh.base.model.request.system.LogReq;
import com.ocean.lmh.base.model.vo.PaginationBean;
import com.ocean.lmh.base.util.DateUtils;

/**
 * 系统日志管理的业务类，负责保存系统中操作员各种操作产生的操作日志记录
 * 并提供日志查看和查询的功能
 * @author liminghui
 * @since 2013-08-28
 */
@Transactional
@Service("logService")
public class LogService{
	
    @Resource(name = "mybatisBaseDao")
    private BaseDaoInterface baseDao;
	
    /**
     * 保存一条操作日志记录
     * @param log 日志实体
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public void addLog(ActionLogValue log)
	{
	    this.baseDao.insertData(SqlKeyConstants.ACTIONLOG_INSERT_ACTIONLOG, log);
	}	
	
	/**
	 * 根据条件分页查询日志记录列表信息
	 * @param logReq 封装查询条件的实体包含[actor,content,type,s_date,e_date]
	 * @return 封装好数据的实体类
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public PaginationBean<ActionLogValue> list( LogReq logReq )
	{
	    Map<String, Object> map = new HashMap<String, Object>();	    
	    map.put("roleName", null);
    	map.put("actor", null);
    	map.put("content", null);
    	if (logReq.getFrom()!=null && logReq.getTo()!=null)
    	{
    		String start = DateUtils.format(DateUtils.FORMAT_DATE,logReq.getFrom());
    		String end = DateUtils.format(DateUtils.FORMAT_DATE,logReq.getTo());
    		map.put("s_date", start+" 00:00:00");
    	    map.put("e_date", end+" 23:59:59");
		}	    
	    PaginationBean<ActionLogValue> pb = new PaginationBean<ActionLogValue>(logReq.getCurrent(), 0, logReq.getPageSize());
	    
	    map.put("page", pb);
	    return this.baseDao.queryForListPageByMap(SqlKeyConstants.ACTIONLOG_SELECT_BYPAGE, map);
	}
	
	/**
	 * 根据id获取一条日志记录信息
	 * @param id 日志id
	 * @return 日志实体
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public ActionLogValue get(Integer id)
	{
	    ActionLogValue actionLog = baseDao.query(SqlKeyConstants.ACTIONLOG_SELECT_BYPRIMARYKEY, id);
	    ActionLogDetailValue detail = baseDao.query(SqlKeyConstants.ACTIONLOGDETAIL_SELECT_DETAILBYID, actionLog.getDetailId());
	    actionLog.setDetail(detail);
	    return actionLog;
	}
	
	/**
	 * 添加一条日志详情记录
	 * @param logDetail 日志详情实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
    public void addLogDetail(ActionLogDetailValue logDetail)
	{
        this.baseDao.insertData(SqlKeyConstants.ACTIONLOGDETAIL_INSERT_LOGDETAIL, logDetail);
    }
    
}
