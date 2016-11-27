package com.ocean.lmh.system.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.SeriesCodeValue;
import com.ocean.lmh.base.util.AssertHelper;

/**
 * 系统中视频、图片、排期和信息页面需要用到的编号生成业务类，
 * 主要负责生成唯一的编号。
 * @author liminghui
 *
 */
@Service("seriesCodeService")
@Transactional
public class SeriesCodeService {
	
	@Resource(name = "mybatisBaseDao")
    private BaseDaoInterface baseDao;
	
	/**
	 * 根据序列编号的名称获取该条序列编号记录信息
	 * @param codeName 序列编号名称
	 * @return SeriesCodeValue
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public SeriesCodeValue getSeriesCodeByCodeName(String codeName)
	{
		return this.baseDao.query(SqlKeyConstants.SERIESCODE_SELECT_BYCODENAME, codeName);
	}
	
	/**
	 * 根据序列编号的名称更新一条序列编号记录信息
	 * @param seriesCodeValue 待更新的实体
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSeriesCode(SeriesCodeValue seriesCodeValue)
	{
		this.baseDao.updateData(SqlKeyConstants.SERIESCODE_UPDATE_BYCODENAME, seriesCodeValue);
	}
	
	/**
	 * 获取指定编号名称的下一个序列编号
	 * @param codeName 编号名称
	 * @return 下一个序列编号
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String getNextSeriesCodeByCodeName(String codeName)
	{
		StringBuilder seriesCode = new StringBuilder();
		SeriesCodeValue seriesCodeValue = getSeriesCodeByCodeName(codeName);
		Integer nextCode = getNextNumber(seriesCodeValue.getCode(), seriesCodeValue.getStep());
		String result = makeCodeString(nextCode, seriesCodeValue.getCodeLength());
		if (AssertHelper.isEmptyString(result)) 
		{
			return null;
		}
		else 
		{
			seriesCodeValue.setCode(nextCode.toString());
			updateSeriesCode(seriesCodeValue);
			
			seriesCode.append(seriesCodeValue.getPrefix());
			seriesCode.append(getDateString(new Date()));
			seriesCode.append(result);
		}
		return seriesCode.toString();
	}
	
	/**
	 * 获取指定格式的日期字符串
	 * @param date 传入的日期值
	 * @return 固定格式的日期字符串
	 */
	private String getDateString(Date date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(date);
	}
	
	/**
	 * 获取序列编号的下一个code数值并返回
	 * @param currentCode 当前序列编号
	 * @param step 序列编号步长
	 * @return 加了一次步长后的code数值
	 */
	private Integer getNextNumber(String currentCode,Integer step)
	{
		if (AssertHelper.isEmptyString(currentCode)) 
		{
			//如果当前的编号为空或者""则默认为从一开始
			currentCode = "1";
		}
		return Integer.valueOf(currentCode)+step;
	}
	
	/**
	 * 构造code数值字符串，
	 * @param code 序列编号中的数字编号
	 * @param length 数字编号的最大长度
	 * @return 当code的长度小于指定的length时，构造一个长度为length
	 * 			的字符串，当code长度等于length时返回code字符串，当code的长度大于length时，返回null
	 */
	private String makeCodeString(Integer code,Integer length)
	{
		Integer needLength = length-code.toString().length();
		if (needLength<0) 
		{
			return null;
		}else 
		{
			StringBuilder builder = new StringBuilder();
			for (int i=0; i<needLength; i++) 
			{
				builder.append("0");
			}
			builder.append(code);
			return builder.toString();
		}		
	}

	/**
	 * 重置系统序列编号的code回到系统的最初始状态
	 */
	public void updateCodeToFirstStatus()
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("firstCode", 1);
		this.baseDao.updateData(SqlKeyConstants.SERIESCODE_UPDATE_FORCODE, map);
	}
}
