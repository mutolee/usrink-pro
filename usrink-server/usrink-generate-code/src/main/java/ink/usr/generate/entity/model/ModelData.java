package ink.usr.generate.entity.model;

import freemarker.template.TemplateException;
import ink.usr.generate.App;
import ink.usr.generate.entity.ClassData;
import ink.usr.generate.entity.CodeFile;
import ink.usr.generate.utils.FreemarkerUtil;
import ink.usr.generate.utils.StringUtil;
import lombok.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * model 数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ModelData {

    private CodeFile codeFile;

    public static ModelData buildModeData() throws TemplateException, IOException {
        String tableName = App.tableConfig.getTableName();

        // 构建 ClassDate
        String simpleNamePre = StringUtil.nameCamel(tableName, "_");
        String simpleName = String.format("%sModel", simpleNamePre);
        String pkgName = String.format("%s.common.entity.model.mysql", App.projectConfig.getPackageName());
        ClassData classData = ClassData.buildClassData(simpleName, simpleNamePre, pkgName);

        // 构建 CodeFile
        String ftlName = "XxxModel";
        Map freemarkerData = new HashMap();
        freemarkerData.put("classData", classData);
        freemarkerData.put("projectConfig", App.projectConfig);
        freemarkerData.put("tableModel", App.tableModel);
        String content = FreemarkerUtil.getFtlToString(ftlName, freemarkerData);
        String fileDir = String.format("%s\\%s-common\\%s-common-entity\\src\\main\\java\\%s\\common\\entity\\model\\mysql\\",
                App.projectConfig.getProjectPath(),
                App.projectConfig.getProjectName(),
                App.projectConfig.getProjectName(),
                App.projectConfig.getPackageName().replace(".", "\\"));
        String fileName = String.format("%s.java", simpleName);
        CodeFile codeFile = CodeFile.buildCodeFile(content, fileDir, fileName);

        return ModelData.builder().codeFile(codeFile).build();
    }

}
