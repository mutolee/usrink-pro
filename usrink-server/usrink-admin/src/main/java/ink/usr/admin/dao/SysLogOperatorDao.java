package ink.usr.admin.dao;

import ink.usr.admin.mapper.SysLogOperatorMapper;
import ink.usr.common.domain.admin.SysLogOperatorForm;
import ink.usr.common.model.mysql.SysLogOperatorModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysLogOperatorDao")
public class SysLogOperatorDao {

    @Autowired
    private SysLogOperatorMapper sysLogOperatorMapper;

    /**
     * 查询系统操作日志
     *
     * @param sysLogOperatorForm 系统操作日志
     * @return 系统操作日志
     */
    public List<SysLogOperatorModel> selectSysLogOperatorList(Ds rw, SysLogOperatorForm sysLogOperatorForm) {
        return sysLogOperatorMapper.selectSysLogOperatorList(sysLogOperatorForm);
    }

    /**
     * 新增系统操作日志
     *
     * @param sysLogOperatorModel 系统操作日志
     */
    public int insertSysLogOperator(Ds rw, SysLogOperatorModel sysLogOperatorModel) {
        return sysLogOperatorMapper.insertSysLogOperator(sysLogOperatorModel);
    }

    /**
     * 查询操作日志详细信息
     *
     * @param logId 日志ID
     */
    public SysLogOperatorModel selectSysLogOperatorInfo(Ds rw, Long logId) {
        return sysLogOperatorMapper.selectSysLogOperatorInfo(logId);
    }

    /**
     * 物理删除系统操作日志
     *
     * @param logId 日志ID
     */
    public int deleteSysLogOperator(Ds rw, Long logId) {
        return sysLogOperatorMapper.deleteSysLogOperator(logId);
    }

    /**
     * 根据时间删除操作日志
     */
    public int deleteSysLogOperatorByTime(Ds rw, SysLogOperatorForm sysLogOperatorForm) {
        return sysLogOperatorMapper.deleteSysLogOperatorByTime(sysLogOperatorForm);
    }

}
