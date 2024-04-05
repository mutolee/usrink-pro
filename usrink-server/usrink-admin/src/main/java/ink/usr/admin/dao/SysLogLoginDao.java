package ink.usr.admin.dao;

import ink.usr.admin.mapper.SysLogLoginMapper;
import ink.usr.common.domain.admin.SysLogLoginForm;
import ink.usr.common.model.mysql.SysLogLoginModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysLogLoginDao")
public class SysLogLoginDao {

    @Autowired
    private SysLogLoginMapper sysLogLoginMapper;

    /**
     * 查询系统登录日志
     *
     * @param sysLogLoginForm 系统登录日志
     * @return 系统登录日志
     */
    public List<SysLogLoginModel> selectSysLogLoginList(Ds rw, SysLogLoginForm sysLogLoginForm) {
        return sysLogLoginMapper.selectSysLogLoginList(sysLogLoginForm);
    }

    /**
     * 新增系统登录日志
     *
     * @param sysLogLoginModel 系统登录日志
     */
    public int insertSysLogLogin(Ds rw, SysLogLoginModel sysLogLoginModel) {
        return sysLogLoginMapper.insertSysLogLogin(sysLogLoginModel);
    }

    /**
     * 查询登录日志详细信息
     *
     * @param logId 日志ID
     */
    public SysLogLoginModel selectSysLogLoginInfo(Ds rw, Long logId) {
        return sysLogLoginMapper.selectSysLogLoginInfo(logId);
    }

    /**
     * 物理删除系统登录日志
     *
     * @param logId 日志ID
     */
    public int deleteSysLogLogin(Ds rw, Long logId) {
        return sysLogLoginMapper.deleteSysLogLogin(logId);
    }

    /**
     * 根据时间删除登录日志
     */
    public int deleteSysLogLoginByTime(Ds rw, SysLogLoginForm sysLogLoginForm) {
        return sysLogLoginMapper.deleteSysLogLoginByTime(sysLogLoginForm);
    }

}
