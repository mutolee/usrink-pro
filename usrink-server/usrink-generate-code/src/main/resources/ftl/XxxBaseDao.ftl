package ${classData.pkgName};

import ${projectConfig.packageName}.${projectConfig.moduleName}.mapper.base.${classData.simpleNamePre}BaseMapper;
import ${projectConfig.packageName}.common.entity.model.mysql.${classData.simpleNamePre}Model;
import ${projectConfig.packageName}.framework.mysql.dao.BaseDao;
import ${projectConfig.packageName}.framework.mysql.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * ${classData.simpleNamePre} 的 BaseDao 类，自动生成。
 *
 * @author http://usr.ink
 * @date ${projectConfig.dateTime}
 */
@Repository(value = "${classData.simpleNameLow}")
public class ${classData.simpleName} extends BaseDao <Long, ${classData.simpleNamePre}Model> {

    // -------------------------------------------------------------------
    // 该类为 ${classData.simpleNamePre} 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    @Autowired
    private ${classData.simpleNamePre}BaseMapper ${classData.simpleNamePreLow}BaseMapper;

    @Override
    protected String getPrimaryKeyName() {
    <#if tableModel.primaryFieldModel??>
        return "${tableModel.primaryFieldModel.name}";
    <#else>
        return "id";
    </#if>
    }

    @Override
    protected BaseMapper <${classData.simpleNamePre}Model> getBaseMapper() {
        return ${classData.simpleNamePreLow}BaseMapper;
    }
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    // 该类为 ${classData.simpleNamePre} 的 BaseDao 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
