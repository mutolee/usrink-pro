package ink.usr.common.domain.admin;

import ink.usr.common.model.mysql.SysLogOperatorModel;
import lombok.*;

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
