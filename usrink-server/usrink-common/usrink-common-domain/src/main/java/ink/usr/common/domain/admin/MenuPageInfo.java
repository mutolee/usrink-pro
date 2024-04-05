package ink.usr.common.domain.admin;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单页面信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuPageInfo implements Serializable {

    /**
     * 路由ID
     */
    private String id;

    /**
     * 父级路由ID
     */
    private String parentId;

    /**
     * 路由标签，用于显示
     */
    private String label;

    /**
     * 路由图标
     */
    private String icon;

    /**
     * 路由请求地址
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String path;

    /**
     * 路由对应的页面组件
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String component;

    /**
     * 子路由
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<MenuPageInfo> children;

}
