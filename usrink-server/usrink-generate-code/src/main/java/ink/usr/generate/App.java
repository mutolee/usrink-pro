package ink.usr.generate;


import ink.usr.generate.entity.ProjectConfig;
import ink.usr.generate.entity.TableConfig;
import ink.usr.generate.entity.TableModel;
import ink.usr.generate.entity.dao.DaoData;
import ink.usr.generate.entity.mapper.MapperData;
import ink.usr.generate.entity.model.ModelData;
import ink.usr.generate.entity.service.ServiceData;
import ink.usr.generate.entity.sqlmap.SqlMapData;
import ink.usr.generate.utils.FileUtil;

public class App {

    // 数据库连接信息
    private static String jdbcUrl = "jdbc:mysql://usrink.localhost.dev:3306/usrink-pro-dev?useSSL=false&characterEncoding=UTF-8";
    private static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    private static String jdbcUserName = "root";
    private static String jdbcPassword;
    private static String tableName = "TestTable";

    // 项目配置
    private static String projectPath = "D:\\UsrInk\\usrink-pro\\usrink-server";
    private static String projectName = "usrink";
    private static String moduleName = "admin";
    private static String packageName = "ink.usr";

    // 业务数据
    public static TableConfig tableConfig;
    public static ProjectConfig projectConfig;
    public static TableModel tableModel;

    /**
     * 程序入口
     */
    public static void main(String[] args) throws Exception {
        // 获取命令行参数，数据库密码
        jdbcPassword = args[0];

        // 配置表信息
        tableConfig = TableConfig.buildTableConfig(tableName, jdbcUrl, jdbcDriver, jdbcUserName, jdbcPassword);
        // 项目配置
        projectConfig = ProjectConfig.buildProjectConfig(projectPath, projectName, moduleName, packageName);
        // 表模型
        tableModel = TableModel.build();

        // Model 数据库表实体对象
        ModelData modelData = ModelData.buildModeData();
        FileUtil.writeFile(modelData.getCodeFile());

        // Service 业务逻辑对象
        ServiceData serviceData = ServiceData.buildServiceData();
        FileUtil.writeFile(serviceData.getCodeFile());

        // Dao 数据访问对象
        DaoData daoData = DaoData.buildDaoData();
        FileUtil.writeFile(daoData.getCodeFile());

        // Mapper 数据访问接口
        MapperData mapperData = MapperData.buildMapperData();
        FileUtil.writeFile(mapperData.getCodeFile());

        // SqlMap 数据访问映射文件
        SqlMapData sqlMapData = SqlMapData.buildSqlMapData();
        FileUtil.writeFile(sqlMapData.getCodeFile());
    }


}
