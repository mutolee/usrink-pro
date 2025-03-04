package ink.usr.admin.dao.base;

import ink.usr.admin.mapper.base.SysLogOperatorBaseMapper;
import ink.usr.common.entity.model.mysql.SysLogOperatorModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SysLogOperator 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:00
 */
@Repository(value = "sysLogOperatorBaseDao")
public class SysLogOperatorBaseDao extends BaseDao <Long, SysLogOperatorModel> {

    // -------------------------------------------------------------------
    // 该类为 SysLogOperator 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    @Autowired
    private SysLogOperatorBaseMapper sysLogOperatorBaseMapper;

    @Override
    protected String getPrimaryKeyName() {
        return "logId";
    }

    @Override
    protected BaseMapper <SysLogOperatorModel> getBaseMapper() {
        return sysLogOperatorBaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    // 该类为 SysLogOperator 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
