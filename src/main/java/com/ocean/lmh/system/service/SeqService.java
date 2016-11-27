package com.ocean.lmh.system.service;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.SequenceValue;
/**
 * 请求序列的业务逻辑类
 * @author liminghui
 */
@Transactional
@Service("seqService")
public class SeqService {
	
	private static Logger log	= LoggerFactory.getLogger(SeqService.class);
	
	@Resource(name = "mybatisBaseDao")
	private BaseDaoInterface baseDao;
	
	/**
	 * 获取序列号
	 * @param tableName 要获取何种序列号的标识
	 * @return 生成的序列号
	 */
	public Integer getSeqNo(String tableName)
	{
		log.debug("tableName :: "+tableName);
		return getValue(tableName);
	}
	
	/**
	 * 根据序列表里的当前值和步长构造一个序列编号，返回值为序列表中当前值加上
	 * 步长之后的值，在返回之前会更新序列表中的当前值
	 * @param tableName 表字段名
	 * @return 下一个序列值
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Integer getValue(String tableName)
	{
		SequenceValue seqValue = this.baseDao.query(SqlKeyConstants.SEQUENCE_SELECT_BYPRIMARYKEY, tableName);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("t_name", tableName);
		Integer nextValue = this.baseDao.query(SqlKeyConstants.SEQUENCE_SELECT_NEXTTVALUE, map);
		
		seqValue.setCurrentValue(nextValue);
		this.baseDao.updateData(SqlKeyConstants.SEQUENCE_UPDATE_BYPRIMARYKEY, seqValue);
		
		return nextValue;
	}

	
}
