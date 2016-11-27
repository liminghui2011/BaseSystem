package com.ocean.lmh.system.service;


import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ocean.lmh.base.constant.AppConstant;
import com.ocean.lmh.base.constant.SqlKeyConstants;
import com.ocean.lmh.base.constant.ThreadLocalConstants;
import com.ocean.lmh.base.dao.BaseDaoInterface;
import com.ocean.lmh.base.model.entity.system.UserActionLogValue;
import com.ocean.lmh.base.util.AssertHelper;
import com.ocean.lmh.base.util.HttpRequestUtils;
import com.ocean.lmh.base.util.HttpResponseUtils;
import com.ocean.lmh.base.util.IOSystem;
import com.ocean.lmh.base.util.JSONHelper;
import com.ocean.lmh.base.util.NumberUtils;
import com.ocean.lmh.base.util.StringUtils;
import com.ocean.lmh.base.wrapper.InfoLogServletRequest;
import com.ocean.lmh.base.wrapper.ResponseInfo;
import com.ocean.lmh.system.model.vo.LogConfigParamValue;
import com.ocean.lmh.system.model.vo.LogConfigValue;

/**
 * 操作员使用系统时各项操作的操作日志处理类，对操作员的各项操作进行
 * 日志记录，包括众多的操作属性，详情参考UserActionLog实体类
 * @author liminghui
 */
@Transactional
@Service("userActionLogService")
public class UserActionLogService {
	
	private static Logger log	= LoggerFactory.getLogger(UserActionLogService.class);
	
	private static String S_COL_1 = "s_col_1";
	private static String S_COL_2 = "s_col_2";
	private static String S_COL_3 = "s_col_3";
	private static String S_COL_4 = "s_col_4";
	private static String I_COL_1 = "i_col_1";
	private static String I_COL_2 = "i_col_2";
	private static String I_COL_3 = "i_col_3";
	private static String D_COL_1 = "d_col_1";
	private static String D_COL_2 = "d_col_2";
	private static String D_COL_3 = "d_col_3";

	
    @Resource(name = "mybatisBaseDao")
    private BaseDaoInterface baseDao;	
	
    /**
     * 保存操作员操作时的操作详情日志记录
     * @param request 请求对象
     * @param logConfigValue 系统配置的初始化参数实体对象
     */
	@Transactional(propagation = Propagation.REQUIRED)
	public void save(HttpServletRequest request, LogConfigValue logConfigValue)
	{
		
		HttpSession session  = request.getSession();
		String userId = (String) session.getAttribute(AppConstant.USER_ID);
		
		UserActionLogValue userActionLog = new UserActionLogValue();
		
		userActionLog.setUserId(userId);
		userActionLog.setActionCode(logConfigValue.getActionCode());
		userActionLog.setOperationDesc(logConfigValue.getDesc());
		
		if(HttpRequestUtils.isJsonContent(request))
		{
			log.debug("isJsonParameter:" + true);
			analysisParams(new JsonParameter(request), logConfigValue, userActionLog);
		}else
		{
			log.debug("isJsonParameter:" + false);
			analysisParams(new DefaultParameter(request), logConfigValue, userActionLog);
		}
		
		
		userActionLog.setTxnDate(new Date());
		this.baseDao.insertData(SqlKeyConstants.USERACTIONLOG_INSERT_USERACTIONLOG, userActionLog);	
	}
	
	/**
	 * 更新操作员操作时的日志记录信息
	 * @param request 请求对象
	 * @param responseInfo 请求响应信息
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void update(HttpServletRequest request,ResponseInfo responseInfo)
	{
		
		Object logId = request.getAttribute(ThreadLocalConstants.LOG_ID);
		
		log.debug("update LOG_ID:" + logId);
		if(logId == null)
		{
			return;
		}
		
		UserActionLogValue userActionLog = this.baseDao.query(SqlKeyConstants.USERACTIONLOG_SELECT_BYPRIMARYKEY, logId.toString());
		if(userActionLog == null)
		{
			return;
		}
		
		
		int status = responseInfo.getStatus();
		String msg = responseInfo.getMsg();
		String respData = responseInfo.getResponseDatas();
		
		userActionLog.setHttpRespCode(String.valueOf(status));
		userActionLog.setHttpErrorMsg(msg);
		
		if(HttpResponseUtils.isJsonContent(responseInfo))
		{
			userActionLog.setRespData(StringUtils.checkLength(respData, 1000));
		}
		
		userActionLog.setBusinessResultCode(getResult(respData));
		
		String thirdRespData = (String) request.getAttribute(ThreadLocalConstants.THIRD_RESP_DATA);
		String exceptionInfo = (String) request.getAttribute(ThreadLocalConstants.EXCEPTION_INFO);
		
		userActionLog.setThirdRespData(StringUtils.checkLength(thirdRespData, ThreadLocalConstants.THIRD_RESP_DATA_LENGTH));
		userActionLog.setExceptionInfo(StringUtils.checkLength(exceptionInfo, ThreadLocalConstants.EXCEPTION_INFO_LENGTH));
		
		
		long startTime = Long.valueOf(request.getAttribute(ThreadLocalConstants.START_TIME).toString());
		long usingTime = System.currentTimeMillis() - startTime;
		userActionLog.setUsingTime(usingTime);
		this.baseDao.updateData(SqlKeyConstants.USERACTIONLOG_UPDATE_BYPRIMARYKEY, userActionLog);
	}
	
	/**
	 * 分解从配置文件中或者是请求对象中传过来的多个参数信息
	 * @param paramter 参数实体
	 * @param logConfigValue 配置参数实体
	 * @param userActionLog 操作员日志实体
	 */
	private void analysisParams(Parameter paramter, LogConfigValue logConfigValue, UserActionLogValue userActionLog)
	{
		 List<LogConfigParamValue> paramsList = logConfigValue.getParamNames();
		 
		 StringBuilder inputParams = new StringBuilder();
		 StringBuilder comment = new StringBuilder();
		 for(LogConfigParamValue configParamValue : paramsList)
		 {
			 String paramName = configParamValue.getName();
			 String column = configParamValue.getColumn();
			 Integer maxLength = configParamValue.getMaxLength();
			 
			 Object paramValue = paramter.getParameter(paramName);
			 
			 setColumn(column, paramValue, maxLength, userActionLog);
			 
			 inputParams.append(paramName).append("=").append(paramValue).append(";");

			 if(AssertHelper.isNotEmptyString(column))
			 {
				 comment.append(column).append("=").append(paramName).append(";");
			 }
		 }
		 
		 
		 userActionLog.setInputParams(inputParams.toString());
		 userActionLog.setComment(comment.toString());
	}
	
	/**
	 * 设置从配置文件中获取到的属性信息（如果存在的话就设置）
	 * @param column 属性信息
	 * @param paramValue 参数值
	 * @param maxLength 最大长度
	 * @param userActionLog 操作员日志实体
	 */
	private void setColumn(String column, Object paramValue,Integer maxLength, UserActionLogValue userActionLog)
	{
		log.debug("setColumn column:"+column);
		
		if(column == null || paramValue == null || AssertHelper.isEmptyString(column))
		{
			return ;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		boolean isString = column.startsWith("s");
		boolean isInteger = column.startsWith("i");
		boolean isDate = column.startsWith("d");
		
		if(isString)
		{
			String value = String.valueOf(paramValue);
			if(maxLength > 0 && value.length() > maxLength)
			{
				value = value.substring(0, maxLength - 1);
			}
			
			if(S_COL_1.equals(column))
			{
				userActionLog.setStringColumn1(value);
			}else if(S_COL_2.equals(column))
			{
				userActionLog.setStringColumn2(value);
			}else if(S_COL_3.equals(column))
			{
				userActionLog.setStringColumn3(value);
			}else if(S_COL_4.equals(column))
			{
				userActionLog.setStringColumn4(value);
			}else
			{
				throw new RuntimeException("Unknow column:"+column+"; please check user-action-log.xml!");
			}
			
		}else if(isInteger)
		{
			Integer value  = NumberUtils.parseInt(paramValue);
			
			if(I_COL_1.equals(column))
			{
				userActionLog.setIntColumn1(value);
			}else if(I_COL_2.equals(column))
			{
				userActionLog.setIntColumn2(value);
			}else if(I_COL_3.equals(column))
			{
				userActionLog.setIntColumn3(value);
			}else
			{
				throw new RuntimeException("Unknow column:"+column+"; please check user-action-log.xml!");
			}
			
		}else if(isDate)
		{
			
			Date dateTime;
			try 
			{
				dateTime = sdf.parse(paramValue.toString());
			} catch (ParseException e) 
			{
				dateTime = new Date();
			}
			
			if(D_COL_1.equals(column))
			{
				userActionLog.setDateColumn1(dateTime);
			}else if(D_COL_2.equals(column))
			{
				userActionLog.setDateColumn2(dateTime);
			}else if(D_COL_3.equals(column))
			{
				userActionLog.setDateColumn3(dateTime);
			}else
			{
				throw new RuntimeException("Unknow column:"+column+"; please check user-action-log.xml!");
			}
			
		}else
		{
			throw new RuntimeException("Unknow column:"+column+"; please check user-action-log.xml!");
		}
	}
	
	private Integer getResult(String json)
	{
		try
		{
			Map<?,?> map = JSONHelper.toObject(json, Map.class);
			
			Object result = map.get("result");
			return NumberUtils.parseInt(result);
		}catch(Exception e)
		{
			return null;
		}
		
	}
	
	private interface Parameter
	{
		String getParameter(String name);
	}
	
	private class JsonParameter implements Parameter
	{
		private Map<String, Object> map;
		
		
		@SuppressWarnings("unchecked")
		public JsonParameter(HttpServletRequest request)
		{
			try 
			{
				InfoLogServletRequest infoRequest = (InfoLogServletRequest)request;
				BufferedReader br = infoRequest.getReader();
				String content = IOSystem.read(br);
				
				map = JSONHelper.toObject(content, Map.class);
				log.debug("map:" + map);
				
				if(map == null)
				{
					map = new HashMap<String, Object>();
				}
				log.debug("map:" + map);
				
			} catch (Exception e) 
			{
				log.error("request.getReader error:", e);
				throw new RuntimeException("request.getReader error:" + e);
			}
		}
		
		public String getParameter(String name) 
		{
			return (String)map.get(name);
		}
	}
	
	private class DefaultParameter implements Parameter
	{
		private HttpServletRequest request;
		
		public DefaultParameter(HttpServletRequest request)
		{
			this.request = request;
		}
		
		public String getParameter(String name) 
		{
			return request.getParameter(name);
		}
		
		
	}
	
}
