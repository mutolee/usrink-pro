package ink.usr.generate.entity;

import ink.usr.generate.utils.StringUtil;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ClassData {

    // 类简称，如 DemoModel
    protected String simpleName;

    // 类简称，小写，如 demoModel
    protected String simpleNameLow;

    // 类简称前缀，如 Demo
    protected String simpleNamePre;

    // l类简称前缀，小写，如 demo
    protected String simpleNamePreLow;

    // 包名称，如 ink.usr.common.entity.model.mysql
    protected String pkgName;

    // 完整类名，如 ink.usr.common.entity.model.mysql.DemoModel
    protected String className;


    public static ClassData buildClassData(String simpleName, String simpleNamePre, String pkgName) {
        return ClassData
                .builder()
                .simpleName(simpleName)
                .simpleNameLow(StringUtil.uncapitalize(simpleName))
                .simpleNamePre(simpleNamePre)
                .simpleNamePreLow(StringUtil.uncapitalize(simpleNamePre))
                .pkgName(pkgName)
                .className(pkgName + "." + simpleName)
                .build();
    }

}
