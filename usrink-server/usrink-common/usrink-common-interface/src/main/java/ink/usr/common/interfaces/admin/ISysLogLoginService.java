package ink.usr.common.interfaces.admin;

import ink.usr.common.domain.admin.SysLogLoginForm;
import ink.usr.common.model.mysql.SysLogLoginModel;

import java.util.List;

public interface ISysLogLoginService {

    /**
     * 查询系统登录日志
     *
     * @param sysLogLoginForm 系统登录日志
     * @return 系统登录日志
     */
    List<SysLogLoginModel> selectSysLogLoginList(SysLogLoginForm sysLogLoginForm);

    /**
     * 新增系统登录日志
     *
     * @param sysLogLoginModel 系统登录日志
     */
    int insertSysLogLogin(SysLogLoginModel sysLogLoginModel);

    /**
     * 查询登录日志详细信息
     *
     * @param logId 日志ID
     */
    SysLogLoginModel selectSysLogLoginInfo(Long logId);

    /**
     * 物理删除系统登录日志
     *
     * @param logId 日志ID
     */
    int deleteSysLogLogin(Long logId);

    /**
     * 根据时间删除登录日志
     */
    int deleteSysLogLoginByTime(SysLogLoginForm sysLogLoginForm);

}
