package ink.usr.admin.service.base;

import ink.usr.admin.dao.base.SysRoleBaseDao;
import ink.usr.common.entity.model.mysql.SysRoleModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SysRole 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:54:11
 */
@Service(value = "sysRoleBaseService")
public class SysRoleBaseService extends BaseService<Long, SysRoleModel> {

    // -------------------------------------------------------------------
    // 该类为 SysRole 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    @Autowired
    private SysRoleBaseDao sysRoleBaseDao;

    @Override
    public BaseDao<Long, SysRoleModel> getBaseDao() {
        return sysRoleBaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:54:11
    //
    //
    // 该类为 SysRole 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
