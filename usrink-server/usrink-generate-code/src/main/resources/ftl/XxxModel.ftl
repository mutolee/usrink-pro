package ${classData.pkgName};

import lombok.*;
import java.io.Serializable;

/**
 * ${classData.simpleNamePre}(${tableModel.remark}) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date ${projectConfig.dateTime}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ${classData.simpleName} implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 ${classData.simpleNamePre}(${tableModel.remark}) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    <#list tableModel.fieldModelList as fieldModel>
    /**
     * ${fieldModel.remark}
     */
    private ${fieldModel.javaTypeName} ${fieldModel.name};
    </#list>
    //
    //
    // @author http://usr.ink
    // @date ${projectConfig.dateTime}
    //
    //
    // 该类为 ${classData.simpleNamePre}(${tableModel.remark}) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
