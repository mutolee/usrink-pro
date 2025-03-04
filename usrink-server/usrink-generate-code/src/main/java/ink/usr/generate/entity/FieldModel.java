package ink.usr.generate.entity;

import lombok.*;

/**
 * 字段模型
 */
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class FieldModel {

    /**
     * 字段名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 字段类型
     *
     * @see java.sql.Types
     */
    private int type;

    /**
     * java类型
     */
    private Class javaType;

    /**
     * java类型
     */
    private String javaTypeName;

    /**
     * 是否是主键
     */
    private boolean primary;

}
