package com.ocean.lmh.base.constant;

/**
 * 存储业务操作中与数据库进行交互的sql语句id的常量类
 * @author     [liminghui]（必须）
 */
public class SqlKeyConstants
{
    /** 日志相关的sql语句常量管理  begin */
    public final static String ACTIONLOG_SELECT_BYPRIMARYKEY = "ActionLog.selectByPrimaryKey";
    public final static String ACTIONLOG_SELECT_BYPAGE = "ActionLog.selectListPageByMap";
    public final static String ACTIONLOG_INSERT_ACTIONLOG = "ActionLog.insertActionLog";
    public final static String ACTIONLOGDETAIL_INSERT_LOGDETAIL = "ActionLogDetail.insertLogDetail";
    public final static String ACTIONLOGDETAIL_SELECT_DETAILBYID = "ActionLogDetail.getDetailById";
    public final static String USERACTIONLOG_SELECT_BYPRIMARYKEY = "UserActionLog.selectByPrimaryKey";
    public final static String USERACTIONLOG_DELETE_BYPRIMARYKEY = "UserActionLog.deleteByPrimaryKey";
    public final static String USERACTIONLOG_INSERT_USERACTIONLOG = "UserActionLog.insertUserActionLog";
    public final static String USERACTIONLOG_UPDATE_BYPRIMARYKEY = "UserActionLog.updateByPrimaryKey";
    /** 日志相关的sql语句常量管理  end */
    
    /** 菜单相关的sql语句常量管理  begin  */
    public final static String MENU_SELECT_BYPRIMARYKEY = "Menu.selectByPrimaryKey";
    public final static String MENU_SELECT_BYPAGE = "Menu.selectListPageByMap";
    public final static String MENU_SELECT_MAXPOSITION = "Menu.selectMaxPosition";
    public final static String MENU_SELECT_ALLMENUBYCONDITIONS = "Menu.selectAllMenuByConditions";
    public final static String MENU_SELECT_ROOTMENUNODE = "Menu.selectRootMenuNode";
    public final static String MENU_SELECT_MENUHASCHILD = "Menu.selectMenuHasChild";
    public final static String MENU_SELECT_BYNAMEANDPID = "Menu.selectByNameAndPid";
    public final static String MENU_SELECT_BYNAMEANDNOTID = "Menu.selectByNameAndNotId";
    public final static String MENU_INSERT_MENU = "Menu.insertMenu";
    public final static String MENU_UPDATE_MENU = "Menu.updateMenu";
    public final static String MENU_DELETE_BYPRIMARYKEY = "Menu.deleteByPrimaryKey";
    public final static String MENU_DELETE_BYPARENTID = "Menu.deleteByParentId";
    /** 菜单相关的sql语句常量管理  end  */
    
    /** 系统用户相关的sql语句常量管理  begin */
    public final static String SYSTEMUSER_SELECT_PAGE = "SystemUser.selectListPageByMap";
    public final static String SYSTEMUSER_SELECT_BYPRIMARYKEY = "SystemUser.selectByPrimaryKey";
    public final static String SYSTEMUSER_SELECT_ALLBYPROPERTY = "SystemUser.selectAllByProperty";
    public final static String SYSTEMUSER_SELECT_BYNAMEANDID = "SystemUser.selectByNameAndId";
    public final static String SYSTEMUSER_INSERT_SYSTEMUSER = "SystemUser.insertSystemUser";
    public final static String SYSTEMUSER_DELETE_BYPRIMARYKEY = "SystemUser.deleteByPrimaryKey";
    public final static String SYSTEMUSER_UPDATE_BYPRIMARYKEY = "SystemUser.updateByPrimaryKey";
    public final static String SYSTEMUSER_UPDATE_BYUSERID = "SystemUser.updateByUserid";
    public final static String SYSTEMUSER_UPDATE_BYSTATUS = "SystemUser.updateByStatus";
    /** 系统用户相关的sql语句常量管理  end */
    
    /** 系统角色与用户相关的sql语句常量管理  begin */
    public final static String ROLE_USER_INSERT = "UserRole.insertUserRole";
    public final static String ROLE_USER_DELETE = "UserRole.deleteByPrimaryKey";
    public final static String ROLE_USER_SELECT = "UserRole.selectByRoleId";
    /** 系统角色与用户相关的sql语句常量管理  end */
    
    /** 系统角色相关的sql语句常量管理  begin */
    public final static String ROLE_SELECT_BYPAGE = "Role.selectListPageByMap";
    public final static String ROLE_SELECT_BYPRIMARYKEY = "Role.selectByPrimaryKey";
    public final static String ROLE_SELECT_BYNAMEANDID = "Role.selectByNameAndId";
    public final static String ROLE_SELECT_ALLROLES = "Role.selectAllRoles";
    public final static String ROLE_SELECT_ROLESBYUSERID = "Role.selectRolesByUserId";
    public final static String ROLE_INSERT_ROLE = "Role.insertRole";
    public final static String ROLE_DELETE_BYPRIMARYKEY = "Role.deleteByPrimaryKey";
    public final static String ROLE_UPDATE_BYPRIMARYKEY = "Role.updateByPrimaryKey";
    /** 系统角色相关的sql语句常量管理  end */
    
    /** 系统角色与权限相关的sql语句常量管理  begin */
    public final static String ROLE_PRIVILEGE_INSERT = "RolePrivilege.insertRolePrivilege";
    public final static String ROLE_PRIVILEGE_DELETE = "RolePrivilege.deleteByPrimaryKey";
    public final static String OPER_PRIVILEGE_INSERT = "OperPrivilege.insertOperPrivilege";
    public final static String OPER_PRIVILEGE_SELECT = "OperPrivilege.selectByPrivilegeId";
    /** 系统角色与权限相关的sql语句常量管理  end */
    
    /** 权限相关的sql语句常量管理 begin */
    public final static String PRIVILEGE_SELECT_BYPAGE = "Privilege.selectListPageByMap";
    public final static String PRIVILEGE_SELECT_BYNAMEANDID = "Privilege.selectByNameAndId";
    public final static String PRIVILEGE_SELECT_ORDERGROUPID = "Privilege.selectOrderByGroupAndId";
    public final static String PRIVILEGE_SELECT_BYLISTIDS = "Privilege.selectByListIds";
    public final static String PRIVILEGE_SELECT_BYROLEID = "Privilege.selectByRoleId";
    public final static String PRIVILEGE_INSERT = "Privilege.insert";
    public final static String PRIVILEGE_UPDATE = "Privilege.update";
    public final static String PRIVILEGE_DELETE_BYID = "Privilege.deleteById";
    public final static String PRIVILEGE_GET_PRIVILEGEURILIST = "Privilege.getPrivilegeUriList";
    public final static String PRIVILEGE_GET_BYID = "Privilege.getById";
    
    public final static String OPERPRIVILEGE_INSERT = "OperPrivilege.insertOperPrivilege";
    public final static String OPERPRIVILEGE_DELETE_BYPRIMARYKEY = "OperPrivilege.deleteByPrimaryKey";
    public final static String OPERPRIVILEGE_DELETE_BYPRIVILEGEID = "OperPrivilege.deleteByPrivilegeId";
    public final static String OPERPRIVILEGE_SELECT_BYPARENTID = "OperPrivilege.selectByPrivilegeId";
    public final static String OPERPRIVILEGE_SELECT_BYIDS = "OperPrivilege.selectByIds";
    public final static String OPERPRIVILEGE_SELECT_BYNAMEANDID = "OperPrivilege.selectByNameAndId";
    public final static String OPERPRIVILEGE_SELECT_BYPRIMARYKEY = "OperPrivilege.selectByPrimaryKey";
    public final static String OPERPRIVILEGE_SELECT_BYROLEID = "OperPrivilege.selectOperPrivilegeByRoleId";
    public final static String OPERPRIVILEGE_SELECT_ALLURI = "OperPrivilege.selectAllOperPrivilegeUri";
    public final static String OPERPRIVILEGE_SELECT_BYPRIVILEGEID = "OperPrivilege.selectByRPid";
    public final static String OPERPRIVILEGE_UPDATE_BYPRIMARYKEY = "OperPrivilege.updateByPrimaryKey";
    
    public final static String SYSTEMSTATUS_SELECT_BYLEVEL = "SystemStatus.selectByStatusLevel";
    public final static String SYSTEMSTATUSITEM_SELECT_BYPARENTSTATUSID = "SystemStatusItem.selectByParentStatusId";
    /** 权限相关的sql语句常量管理 end */
    
    /**param 参数管理常量begin*/
    public final static String PARAM_SELECT_PAGE = "Param.selectListPageByMap";
    public final static String PARAM_SELECT_BYID = "Param.selectById";
    public final static String PARAM_DELETE_BYID = "Param.deleteById";
    public final static String PARAM_SELECT_BYNAMEANDID = "Param.selectByIdAndOther";
    public final static String PARAM_UPDATE = "Param.update";
    public final static String PARAM_INSERT = "Param.insert";
    public final static String PARAM_SELECT_ALLPARAMS = "Param.selectAllParams";
    public final static String PARAMITEM_SELECT_BYPARAMID = "ParamItem.selectByParamId";
    public final static String PARAMITEM_INSERT = "ParamItem.insert";
    public final static String PARAMITEM_DELETE_BYPARAMID = "ParamItem.deleteByParamId";
    public final static String PARAMITEM_UPDATE = "ParamItem.update";
    public final static String PARAMITEM_DELETEBYID = "ParamItem.deleteById";
    /**param 参数管理 常量end*/
    
    /** 系统Seq、SystemParamter相关的sql语句常量管理  begin */
    public final static String SEQUENCE_SELECT_BYPRIMARYKEY = "Sequence.selectByPrimaryKey";
    public final static String SEQUENCE_SELECT_CURRENTVALUE = "Sequence.selectCurrentValue";
    public final static String SEQUENCE_SELECT_NEXTTVALUE = "Sequence.selectNextValue";
    public final static String SEQUENCE_UPDATE_BYPRIMARYKEY = "Sequence.updateByPrimaryKey";
    /** 系统Seq、SystemParamter相关的sql语句常量管理  end */
    
    /** 系统序列编号相关的sql语句常量管理 begin */
    public final static String SERIESCODE_SELECT_BYCODENAME = "SeriesCode.selectByPrimaryKey";
    public final static String SERIESCODE_UPDATE_BYCODENAME = "SeriesCode.updateByPrimaryKey";
    public final static String SERIESCODE_UPDATE_FORCODE = "SeriesCode.updateCodeToFirst";
    /** 系统序列编号相关的sql语句常量管理 end */
}
