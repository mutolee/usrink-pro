package ink.usr.generate.entity;

import ink.usr.generate.App;
import lombok.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 表模型
 */
@Data
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TableModel {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字段列表
     */
    private List<FieldModel> fieldModelList;

    /**
     * 主键字段
     */
    private FieldModel primaryFieldModel;

    public static TableModel build() throws SQLException, ClassNotFoundException {
        TableConfig tableConfig = App.tableConfig;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tableName = tableConfig.getTableName();
        TableModel tableModel = TableModel
                .builder()
                .tableName(tableName)
                .fieldModelList(new ArrayList<>())
                .build();

        try {
            conn = getConnection(tableConfig);
            DatabaseMetaData dbmd = conn.getMetaData();

            // 获取表的描述信息
            ResultSet tableRs = dbmd.getTables(null, "%", tableName, new String[]{"TABLE"});
            if (tableRs.next()) {
                String tableRemark = tableRs.getString("REMARKS"); // 获取表的描述信息
                tableModel.setRemark(tableRemark); // 设置表的描述信息
            }
            tableRs.close();

            // 获取表的列信息
            rs = dbmd.getColumns(null, "%", tableName, "%");

            // 用于存储已处理的列名
            Set<String> processedColumns = new HashSet<>();

            while (rs.next()) {
                String column_name = rs.getString("COLUMN_NAME");

                // 检查是否已处理过该列
                if (processedColumns.contains(column_name)) {
                    continue; // 跳过重复列
                }
                processedColumns.add(column_name);

                String remark = rs.getString("REMARKS");
                boolean primary = rs.getString("IS_AUTOINCREMENT").toUpperCase().equals("YES");
                int type = rs.getInt("DATA_TYPE");

                FieldModel fieldModel = FieldModel.builder()
                        .name(column_name)
                        .remark(remark)
                        .primary(primary)
                        .type(type)
                        .javaType(JdbcTypeJavaTypeMap.jdbcTypeJavaTypeMap.get(type))
                        .build();
                fieldModel.setJavaTypeName(fieldModel.getJavaType().getTypeName());
                tableModel.getFieldModelList().add(fieldModel);

                if (fieldModel.isPrimary()) {
                    tableModel.setPrimaryFieldModel(fieldModel);
                }
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw e;
            }
        }
        return tableModel;
    }

    private static Connection getConnection(TableConfig tableConfig) throws ClassNotFoundException, SQLException {
        Class.forName(tableConfig.getJdbcDriver());
        return DriverManager.getConnection(tableConfig.getJdbcUrl(), tableConfig.getJdbcUserName(), tableConfig.getJdbcPassword());
    }

}
