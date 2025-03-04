package ink.usr.generate.entity;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * jdbc 类型和 java 类型映射关系
 */
public class JdbcTypeJavaTypeMap {

    public final static Map<Integer, Class> jdbcTypeJavaTypeMap = new HashMap<>();

    static {
        jdbcTypeJavaTypeMap.put(Types.BIGINT, Long.class);
        jdbcTypeJavaTypeMap.put(Types.BINARY, byte[].class);
        jdbcTypeJavaTypeMap.put(Types.BIT, Boolean.class);
        jdbcTypeJavaTypeMap.put(Types.BLOB, byte[].class);
        jdbcTypeJavaTypeMap.put(Types.CHAR, String.class);
        jdbcTypeJavaTypeMap.put(Types.CLOB, String.class);
        jdbcTypeJavaTypeMap.put(Types.DATE, java.sql.Date.class);
        jdbcTypeJavaTypeMap.put(Types.DECIMAL, Double.class);
        jdbcTypeJavaTypeMap.put(Types.DOUBLE, Double.class);
        jdbcTypeJavaTypeMap.put(Types.FLOAT, Double.class);
        jdbcTypeJavaTypeMap.put(Types.INTEGER, Integer.class);
        jdbcTypeJavaTypeMap.put(Types.JAVA_OBJECT, Object.class);
        jdbcTypeJavaTypeMap.put(Types.LONGVARBINARY, byte[].class);
        jdbcTypeJavaTypeMap.put(Types.LONGVARCHAR, String.class);
        jdbcTypeJavaTypeMap.put(Types.NUMERIC, Double.class);
        jdbcTypeJavaTypeMap.put(Types.OTHER, Object.class);
        jdbcTypeJavaTypeMap.put(Types.REAL, Float.class);
        jdbcTypeJavaTypeMap.put(Types.SMALLINT, Integer.class);
        jdbcTypeJavaTypeMap.put(Types.TIME, java.sql.Time.class);
        jdbcTypeJavaTypeMap.put(Types.TIMESTAMP, java.sql.Timestamp.class);
        jdbcTypeJavaTypeMap.put(Types.TINYINT, Integer.class);
        jdbcTypeJavaTypeMap.put(Types.VARBINARY, byte[].class);
        jdbcTypeJavaTypeMap.put(Types.VARCHAR, String.class);
    }
}
