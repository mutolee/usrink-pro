package ink.usr.admin.service;

import ink.usr.admin.dao.SysLogOperatorDao;
import ink.usr.admin.service.base.SysLogOperatorBaseService;
import ink.usr.common.entity.admin.form.SysLogOperatorForm;
import ink.usr.common.entity.admin.interfaces.ISysLogOperatorService;
import ink.usr.common.entity.model.mysql.SysLogOperatorModel;
import ink.usr.framework.mysql.entity.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysLogOperatorService")
public class SysLogOperatorService extends SysLogOperatorBaseService implements ISysLogOperatorService {

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
