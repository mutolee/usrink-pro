package ${classData.pkgName};

import ${projectConfig.packageName}.${projectConfig.moduleName}.dao.base.${classData.simpleNamePre}BaseDao;
import ${projectConfig.packageName}.common.entity.model.mysql.${classData.simpleNamePre}Model;
import ${projectConfig.packageName}.framework.mysql.dao.BaseDao;
import ${projectConfig.packageName}.framework.mysql.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ${classData.simpleNamePre} 的 BaseService 类，自动生成。
 *
 * @author http://usr.ink
 * @date ${projectConfig.dateTime}
 */
@Service(value = "${classData.simpleNameLow}")
public class ${classData.simpleName} extends BaseService<Long, ${classData.simpleNamePre}Model> {

    // -------------------------------------------------------------------
    // 该类为 ${classData.simpleNamePre} 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    @Autowired
    private ${classData.simpleNamePre}BaseDao ${classData.simpleNamePreLow}BaseDao;

    @Override
    public BaseDao<Long, ${classData.simpleNamePre}Model> getBaseDao() {
        return ${classData.simpleNamePreLow}BaseDao;
    }
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    // 该类为 ${classData.simpleNamePre} 的 BaseService 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
