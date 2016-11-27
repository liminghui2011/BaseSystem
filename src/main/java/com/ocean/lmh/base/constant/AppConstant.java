package com.ocean.lmh.base.constant;

public final class AppConstant {

	/** 系统账号标 识 */
	public final static String	USER_ID				= "USER_ID";
	
	public final static String	USER_INFO			= "USER_INFO";

	public final static String	REQUEST_SCOPE		= "REQUEST";

	public final static String	SESSION_SCOPE		= "SESSION";

	public final static String	APPLICATION_SCOPE	= "APPLICATION";

	/** 操作员的状态标识，0为正常，1为停用，2为已删除 */
	public static final String	USER_ENABLE			= "0";
	public static final String	USER_DISABLE		= "1";
	public static final String	USER_DELETE		= "2";
	
	/** 系统默认的操作员密码 */
	public static final String  SYSTEM_INIT_PASSWORD = "888888";
	
	/** 账号是否要重置密码  begin */
    public final static String USER_PASSWORD_ON = "1";
    public final static String USER_PASSWORD_OFF = "0";
    /** 账号是否要重置密码  end */
	

	/** 系统所有参数在程序中的键标识 */
	public static final String	PARAMETER_MAP		= "PARAMETER_MAP";

	/** 系统里所有省份在程序中的键标识 */
	public static final String	PROVINCE_MAP		= "PROVINCE_MAP";

	/** 系统里所有地市在程序中的键标识 */
	public static final String	CITY_MAP			= "CITY_MAP";

	public static final String	VERIFY_CODE			= "VERIFY_CODE";

	public static final String	SYSTEM_MENU_TREE	= "SYSTEM_MENU_TREE";

	public static final String	USER_MENU_TREE		= "USER_MENU_TREE";

	public static final String	USER_HOLDER			= "USER_HOLDER";

	public static final String	SESSIONID_HOLDER	= "SESSIONID_HOLDER";

	/** 系统所有权限的访问URI列表 */
	public static final String	PRIVILEGE_URI_LIST	= "PRIVILEGE_URI_LIST";
	
	/** 日志配置文件  */
	public static final String	LOG_CONFIG_VALUE	= "LOG_CONFIG_VALUE";
	
	public static final String	SYSTEM_PARAMTER_MAP	= "SYSTEM_PARAMTER_MAP";
	
	public static final String SYSTEM_STATUS_MAP = "SYSTEM_STATUS_MAP";
	
	public static final String FILE_HEADER_TYPE = "Content-Type";
	
	public static final String FILE_HEADER_CODE = "text/plain;charset=utf-8";
	
	/** 当权限不足或者是权限审核流程不正确时返回的url  */
	public static final String SYSTEM_INFO_URL = "/system/info/denied.do";
	
	/**  系统权限开关常量值   */
	public static final String SYSTEM_STATUS_LEVEL = "system.status.level";
	
	/** 系统缓存中存储用户账号以备定时更新的系统常量   */
	public static final String CACHEMAP_USERID_DATE = "SYSTEM_CACHEMAP";
	
	/** 运营管理模块中区域信息在缓存中的作为键的值   */
	public static final String OPERRATION_AREA_KEY = "OPERRATION_AREA_KEY";
	
	/** 运营管理模块中业务信息在缓存中的作为键的值   */
	public static final String OPERRATION_BUSINESS_KEY = "OPERRATION_BUSINESS_KEY";
	public static final String COLORVALUE = "colorValue";
}
