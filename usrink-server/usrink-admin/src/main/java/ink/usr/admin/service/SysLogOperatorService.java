package ink.usr.admin.service;

import ink.usr.admin.dao.SysLogOperatorDao;
import ink.usr.common.domain.admin.SysLogOperatorForm;
import ink.usr.common.interfaces.admin.ISysLogOperatorService;
import ink.usr.common.model.mysql.SysLogOperatorModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysLogOperatorService")
public class SysLogOperatorService implements ISysLogOperatorService {

    @Autowired
    private SysLogOperatorDao sysLogOperatorDao;

    /**
     * 查询系统操作日志
     *
     * @param sysLogOperatorForm 系统操作日志
     * @return 系统操作日志
     */
    public List<SysLogOperatorModel> selectSysLogOperatorList(SysLogOperatorForm sysLogOperatorForm) {
        return sysLogOperatorDao.selectSysLogOperatorList(Ds.W, sysLogOperatorForm);
    }

    /**
     * 新增系统操作日志
     *
     * @param sysLogOperatorModel 系统操作日志
     */
    public int insertSysLogOperator(SysLogOperatorModel sysLogOperatorModel) {
        return sysLogOperatorDao.insertSysLogOperator(Ds.W, sysLogOperatorModel);
    }

    /**
     * 查询操作日志详细信息
     *
     * @param logId 日志ID
     */
    public SysLogOperatorModel selectSysLogOperatorInfo(Long logId) {
        return sysLogOperatorDao.selectSysLogOperatorInfo(Ds.W, logId);
    }

    /**
     * 物理删除系统操作日志
     *
     * @param logId 日志ID
     */
    public int deleteSysLogOperator(Long logId) {
        return sysLogOperatorDao.deleteSysLogOperator(Ds.W, logId);
    }

    /**
     * 根据时间删除操作日志
     */
    public int deleteSysLogOperatorByTime(SysLogOperatorForm sysLogOperatorForm) {
        return sysLogOperatorDao.deleteSysLogOperatorByTime(Ds.W, sysLogOperatorForm);
    }
}
