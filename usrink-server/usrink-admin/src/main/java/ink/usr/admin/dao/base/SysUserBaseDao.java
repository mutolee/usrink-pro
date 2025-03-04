package ink.usr.admin.dao.base;

import ink.usr.admin.mapper.base.SysUserBaseMapper;
import ink.usr.common.entity.model.mysql.SysUserModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SysUser 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:56:31
 */
@Repository(value = "sysUserBaseDao")
public class SysUserBaseDao extends BaseDao <Long, SysUserModel> {

    // -------------------------------------------------------------------
    // 该类为 SysUser 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    @Autowired
    private SysUserBaseMapper sysUserBaseMapper;

    @Override
    protected String getPrimaryKeyName() {
        return "userId";
    }

    @Override
    protected BaseMapper <SysUserModel> getBaseMapper() {
        return sysUserBaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    // 该类为 SysUser 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
