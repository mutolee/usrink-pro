package ink.usr.admin.service;

import ink.usr.admin.dao.SysLogLoginDao;
import ink.usr.common.domain.admin.SysLogLoginForm;
import ink.usr.common.interfaces.admin.ISysLogLoginService;
import ink.usr.common.model.mysql.SysLogLoginModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysLogLoginService")
public class SysLogLoginService implements ISysLogLoginService {

    @Autowired
    private SysLogLoginDao sysLogLoginDao;

    /**
     * 查询系统登录日志
     *
     * @param sysLogLoginForm 系统登录日志
     * @return 系统登录日志
     */
    public List<SysLogLoginModel> selectSysLogLoginList(SysLogLoginForm sysLogLoginForm) {
        return sysLogLoginDao.selectSysLogLoginList(Ds.W, sysLogLoginForm);
    }

    /**
     * 新增系统登录日志
     *
     * @param sysLogLoginModel 系统登录日志
     */
    public int insertSysLogLogin(SysLogLoginModel sysLogLoginModel) {
        return sysLogLoginDao.insertSysLogLogin(Ds.W, sysLogLoginModel);
    }

    /**
     * 查询登录日志详细信息
     *
     * @param logId 日志ID
     */
    public SysLogLoginModel selectSysLogLoginInfo(Long logId) {
        return sysLogLoginDao.selectSysLogLoginInfo(Ds.W, logId);
    }

    /**
     * 物理删除系统登录日志
     *
     * @param logId 日志ID
     */
    public int deleteSysLogLogin(Long logId) {
        return sysLogLoginDao.deleteSysLogLogin(Ds.W, logId);
    }

    /**
     * 根据时间删除登录日志
     */
    public int deleteSysLogLoginByTime(SysLogLoginForm sysLogLoginForm) {
        return sysLogLoginDao.deleteSysLogLoginByTime(Ds.W, sysLogLoginForm);
    }

}
