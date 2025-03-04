package ink.usr.admin.service.base;

import ink.usr.admin.dao.base.SysLogLoginBaseDao;
import ink.usr.common.entity.model.mysql.SysLogLoginModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SysLogLogin 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:49:52
 */
@Service(value = "sysLogLoginBaseService")
public class SysLogLoginBaseService extends BaseService<Long, SysLogLoginModel> {

    // -------------------------------------------------------------------
    // 该类为 SysLogLogin 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    @Autowired
    private SysLogLoginBaseDao sysLogLoginBaseDao;

    @Override
    public BaseDao<Long, SysLogLoginModel> getBaseDao() {
        return sysLogLoginBaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:49:52
    //
    //
    // 该类为 SysLogLogin 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
