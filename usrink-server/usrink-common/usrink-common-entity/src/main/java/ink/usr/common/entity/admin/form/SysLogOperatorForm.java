package ink.usr.common.entity.admin.form;

import ink.usr.common.entity.model.mysql.SysLogOperatorModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 操作日志表单
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysLogOperatorForm extends SysLogOperatorModel {

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

}
