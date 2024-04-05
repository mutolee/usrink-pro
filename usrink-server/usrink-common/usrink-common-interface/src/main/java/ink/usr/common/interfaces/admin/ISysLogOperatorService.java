package ink.usr.common.interfaces.admin;

import ink.usr.common.domain.admin.SysLogOperatorForm;
import ink.usr.common.model.mysql.SysLogOperatorModel;

import java.util.List;

public interface ISysLogOperatorService {

    /**
     * 查询系统操作日志
     *
     * @param sysLogOperatorForm 系统操作日志
     * @return 系统操作日志
     */
    List<SysLogOperatorModel> selectSysLogOperatorList(SysLogOperatorForm sysLogOperatorForm);

    /**
     * 新增系统操作日志
     *
     * @param sysLogOperatorModel 系统操作日志
     */
    int insertSysLogOperator(SysLogOperatorModel sysLogOperatorModel);

    /**
     * 查询操作日志详细信息
     *
     * @param logId 日志ID
     */
    SysLogOperatorModel selectSysLogOperatorInfo(Long logId);

    /**
     * 物理删除系统操作日志
     *
     * @param logId 日志ID
     */
    int deleteSysLogOperator(Long logId);

    /**
     * 根据时间删除操作日志
     */
    int deleteSysLogOperatorByTime(SysLogOperatorForm sysLogOperatorForm);
}
