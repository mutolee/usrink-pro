package ink.usr.common.domain.admin;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 系统用户的菜单信息
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserMenus implements Serializable {

    /**
     * 普通页面列表，不在侧边栏显示
     */
    private List<MenuPageInfo> pages;

    /**
     * 主菜单列表，用于生成侧边栏
     */
    private List<MenuPageInfo> menus;

}
