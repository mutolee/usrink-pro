package ink.usr.generate.entity.service;

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
 * Service 数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ServiceData {

    private CodeFile codeFile;

    public static ServiceData buildServiceData() throws TemplateException, IOException {
        String tableName = App.tableConfig.getTableName();

        // 构建 ClassDate
        String simpleNamePre = StringUtil.nameCamel(tableName, "_");
        String simpleName = String.format("%sBaseService", simpleNamePre);
        String pkgName = String.format("%s.%s.service.base", App.projectConfig.getPackageName(), App.projectConfig.getModuleName());
        ClassData classData = ClassData.buildClassData(simpleName, simpleNamePre, pkgName);

        // 构建 CodeFile
        String ftlName = "XxxBaseService";
        Map freemarkerData = new HashMap();
        freemarkerData.put("classData", classData);
        freemarkerData.put("projectConfig", App.projectConfig);
        freemarkerData.put("tableModel", App.tableModel);
        String content = FreemarkerUtil.getFtlToString(ftlName, freemarkerData);
        String fileDir = String.format("%s\\%s-%s\\src\\main\\java\\%s\\%s\\service\\base\\",
                App.projectConfig.getProjectPath(),
                App.projectConfig.getProjectName(),
                App.projectConfig.getModuleName(),
                App.projectConfig.getPackageName().replace(".", "\\"),
                App.projectConfig.getModuleName());
        String fileName = String.format("%s.java", simpleName);
        CodeFile codeFile = CodeFile.buildCodeFile(content, fileDir, fileName);

        return ServiceData.builder().codeFile(codeFile).build();
    }

}
