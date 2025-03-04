package ink.usr.admin.dao.base;

import ink.usr.admin.mapper.base.SysLogLoginBaseMapper;
import ink.usr.common.entity.model.mysql.SysLogLoginModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SysLogLogin 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:49:52
 */
@Repository(value = "sysLogLoginBaseDao")
public class SysLogLoginBaseDao extends BaseDao <Long, SysLogLoginModel> {

    // -------------------------------------------------------------------
    // 该类为 SysLogLogin 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    @Autowired
    private SysLogLoginBaseMapper sysLogLoginBaseMapper;

    @Override
    protected String getPrimaryKeyName() {
        return "logId";
    }

    @Override
    protected BaseMapper <SysLogLoginModel> getBaseMapper() {
        return sysLogLoginBaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    // 该类为 SysLogLogin 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
