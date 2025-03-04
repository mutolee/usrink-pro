package ink.usr.admin.service.base;

import ink.usr.admin.dao.base.SysUserBaseDao;
import ink.usr.common.entity.model.mysql.SysUserModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SysUser 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:56:31
 */
@Service(value = "sysUserBaseService")
public class SysUserBaseService extends BaseService<Long, SysUserModel> {

    // -------------------------------------------------------------------
    // 该类为 SysUser 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    @Autowired
    private SysUserBaseDao sysUserBaseDao;

    @Override
    public BaseDao<Long, SysUserModel> getBaseDao() {
        return sysUserBaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:56:31
    //
    //
    // 该类为 SysUser 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
