package ink.usr.admin.dao.base;

import ink.usr.admin.mapper.base.SysRoleBaseMapper;
import ink.usr.common.entity.model.mysql.SysRoleModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SysRole 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:54:11
 */
@Repository(value = "sysRoleBaseDao")
public class SysRoleBaseDao extends BaseDao <Long, SysRoleModel> {

    // -------------------------------------------------------------------
    // 该类为 SysRole 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    @Autowired
    private SysRoleBaseMapper sysRoleBaseMapper;

    @Override
    protected String getPrimaryKeyName() {
        return "roleId";
    }

    @Override
    protected BaseMapper <SysRoleModel> getBaseMapper() {
        return sysRoleBaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    // 该类为 SysRole 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
