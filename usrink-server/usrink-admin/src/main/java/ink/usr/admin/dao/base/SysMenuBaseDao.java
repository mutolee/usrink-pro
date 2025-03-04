package ink.usr.admin.dao.base;

import ink.usr.admin.mapper.base.SysMenuBaseMapper;
import ink.usr.common.entity.model.mysql.SysMenuModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * SysMenu 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:09
 */
@Repository(value = "sysMenuBaseDao")
public class SysMenuBaseDao extends BaseDao <Long, SysMenuModel> {

    // -------------------------------------------------------------------
    // 该类为 SysMenu 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    @Autowired
    private SysMenuBaseMapper sysMenuBaseMapper;

    @Override
    protected String getPrimaryKeyName() {
        return "menuId";
    }

    @Override
    protected BaseMapper <SysMenuModel> getBaseMapper() {
        return sysMenuBaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    // 该类为 SysMenu 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
