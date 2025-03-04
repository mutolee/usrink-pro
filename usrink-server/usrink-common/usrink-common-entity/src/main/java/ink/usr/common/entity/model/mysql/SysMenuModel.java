package ink.usr.common.entity.model.mysql;

import lombok.*;
import java.io.Serializable;

/**
 * SysMenu(系统菜单) 的 Model 类，自动生成。
 *
 * @author http://usr.ink
 * @date 2025-03-04 00:50:09
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysMenuModel implements Serializable {

    // -------------------------------------------------------------------
    // 该类为 SysMenu(系统菜单) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    /**
     * 菜单ID
     */
    private java.lang.Long menuId;
    /**
     * 菜单名称
     */
    private java.lang.String menuName;
    /**
     * 排序，正序排列
     */
    private java.lang.Integer orderIndex;
    /**
     * 菜单图标
     */
    private java.lang.String icon;
    /**
     * 权限标识符
     */
    private java.lang.String permKey;
    /**
     * 父类菜单ID
     */
    private java.lang.Long parentMenuId;
    /**
     * 菜单场景类型，0表示主菜单，1表示独立页面(非主菜单)
     */
    private java.lang.Integer sceneType;
    /**
     * 菜单类型，0表示目录，1表示菜单，2表示按钮
     */
    private java.lang.Integer menuType;
    /**
     * 页面路由
     */
    private java.lang.String route;
    /**
     * 菜单请求页面组件
     */
    private java.lang.String component;
    /**
     * 创建时间
     */
    private java.lang.String createTime;
    /**
     * 更新时间
     */
    private java.lang.String updateTime;
    /**
     * 菜单状态，0表示正常，-1表示禁用
     */
    private java.lang.Integer status;
    //
    //
    // @author http://usr.ink
    // @date 2025-03-04 00:50:09
    //
    //
    // 该类为 SysMenu(系统菜单) 的 Model 类，代码自动生成，请勿修改。
    // 如有需要扩展，请自行继承该类，扩展子方法。
    // -------------------------------------------------------------------
}
