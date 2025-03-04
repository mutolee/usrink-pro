package ink.usr.admin.service.base;

import ink.usr.admin.dao.base.SysMenuBaseDao;
import ink.usr.common.entity.model.mysql.SysMenuModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SysMenu 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:09
 */
@Service(value = "sysMenuBaseService")
public class SysMenuBaseService extends BaseService<Long, SysMenuModel> {

    // -------------------------------------------------------------------
    // 该类为 SysMenu 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    @Autowired
    private SysMenuBaseDao sysMenuBaseDao;

    @Override
    public BaseDao<Long, SysMenuModel> getBaseDao() {
        return sysMenuBaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    // 该类为 SysMenu 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
