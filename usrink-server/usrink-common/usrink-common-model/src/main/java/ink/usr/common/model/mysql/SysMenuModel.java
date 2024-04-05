package ink.usr.common.model.mysql;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuModel implements Serializable {

    /**
     * 菜单ID，主键
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 排序，越小越靠前
     */
    private Integer orderIndex;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 权限标识
     */
    private String permKey;

    /**
     * 父菜单ID
     */
    private Long parentMenuId;

    /**
     * 菜单场景类型，0表示主菜单，1表示独立页面(非主菜单)
     */
    private Integer sceneType;

    /**
     * 菜单类型, 0:目录, 1:菜单, 2:按钮
     */
    private Integer menuType;

    /**
     * 页面路由
     */
    private String route;

    /**
     * 菜单请求页面组件
     */
    private String component;

    /**
     * 菜单状态，0表示正常，-1表示禁用
     */
    private Integer status;

}
