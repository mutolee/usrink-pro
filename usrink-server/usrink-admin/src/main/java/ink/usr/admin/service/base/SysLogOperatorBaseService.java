package ink.usr.admin.service.base;

import ink.usr.admin.dao.base.SysLogOperatorBaseDao;
import ink.usr.common.entity.model.mysql.SysLogOperatorModel;
import ink.usr.framework.mysql.dao.BaseDao;
import ink.usr.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * SysLogOperator 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:00
 */
@Service(value = "sysLogOperatorBaseService")
public class SysLogOperatorBaseService extends BaseService<Long, SysLogOperatorModel> {

    // -------------------------------------------------------------------
    // 该类为 SysLogOperator 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    @Autowired
    private SysLogOperatorBaseDao sysLogOperatorBaseDao;

    @Override
    public BaseDao<Long, SysLogOperatorModel> getBaseDao() {
        return sysLogOperatorBaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:00
    //
    //
    // 该类为 SysLogOperator 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
