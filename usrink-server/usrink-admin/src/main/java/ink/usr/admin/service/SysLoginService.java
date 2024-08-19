package ink.usr.admin.service;

import ink.usr.admin.dao.SysMenuDao;
import ink.usr.common.core.exception.base.BusinessException;
import ink.usr.common.core.utils.CollUtil;
import ink.usr.common.core.utils.StringUtil;
import ink.usr.common.domain.admin.MenuPageInfo;
import ink.usr.common.domain.admin.SysUserMenus;
import ink.usr.common.interfaces.admin.ISysLoginService;
import ink.usr.common.model.mysql.SysMenuModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 登录服务
 */
@Service("sysLoginService")
public class SysLoginService implements ISysLoginService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 查询角色菜单列表
     */
    public SysUserMenus selectUserMenuList(String roleMenuIds) {
        SysUserMenus userMenus = new SysUserMenus();

        // 查询菜单列表
        SysMenuModel paramModel = SysMenuModel.builder().status(0).build();
        List<SysMenuModel> sysMenuModels = sysMenuDao.selectSysMenuList(Ds.W, paramModel);
        if (CollUtil.isEmpty(sysMenuModels)) {
            throw new BusinessException("菜单列表为空");
        }

        // 查询角色拥有的菜单列表
        List<SysMenuModel> filteredList = sysMenuModels.stream()
                .filter(sysMenuModel -> sysMenuModel.getMenuType() == 0 || sysMenuModel.getMenuType() == 1)
                .collect(Collectors.toList());

        // 过滤角色拥有的菜单列表
        if (StringUtil.isNotEmpty(roleMenuIds)) {
            // 角色拥有的菜单ID
            List<String> menuIds = Arrays.asList(roleMenuIds.split(","));
            if (CollUtil.isNotEmpty(menuIds)) {
                filteredList = filteredList.stream()
                        .filter(sysMenuModel -> menuIds.contains(sysMenuModel.getMenuId().toString()))
                        .collect(Collectors.toList());
            }
        }

        // 构建普通页面列表，不在侧边栏显示
        List<MenuPageInfo> pages = new ArrayList<>();
        filteredList.forEach(sysMenuModel -> {
            // 普通页面
            if (sysMenuModel.getSceneType() == 1) {
                // 重组普通页面数据，转换成前端需要的格式
                pages.add(MenuPageInfo.builder()
                        .id(sysMenuModel.getMenuId().toString())
                        .parentId(sysMenuModel.getParentMenuId().toString())
                        .label(sysMenuModel.getMenuName())
                        .icon(sysMenuModel.getIcon())
                        .path(sysMenuModel.getRoute())
                        .component(sysMenuModel.getComponent())
                        .build());
            }
        });
        userMenus.setPages(pages);

        // 构建主菜单树形结构，在侧边栏显示
        // 过滤条件：status 等于 0，并且 menuType 是 0
        List<SysMenuModel> mainMenuList = filteredList.stream()
                // 主菜单
                .filter(sysMenuModel -> sysMenuModel.getSceneType() == 0)
                .collect(Collectors.toList());
        List<MenuPageInfo> menuPageInfoList = new ArrayList<>();
        // 重组主菜单数据，转换成前端需要的格式
        mainMenuList.forEach(sysMenuModel ->
                menuPageInfoList.add(MenuPageInfo.builder()
                        .id(sysMenuModel.getMenuId().toString())
                        .parentId(sysMenuModel.getParentMenuId().toString())
                        .label(sysMenuModel.getMenuName())
                        .icon(sysMenuModel.getIcon())
                        .path(sysMenuModel.getRoute())
                        .component(sysMenuModel.getComponent())
                        .build())
        );

        // 构建树形结构
        List<MenuPageInfo> menus = buildTree(menuPageInfoList);
        userMenus.setMenus(menus);

        return userMenus;
    }

    /**
     * 构建树形结构
     */
    private List<MenuPageInfo> buildTree(List<MenuPageInfo> menuPageInfoList) {
        // 用于存储menuId对应的菜单项
        Map<String, MenuPageInfo> menuMap = new HashMap<>();
        // 存储最终的树形结构
        List<MenuPageInfo> tree = new ArrayList<>();

        // 首先将每个菜单项存储到menuMap中
        menuPageInfoList.forEach(menuPageInfo -> {
            menuPageInfo.setChildren(new ArrayList<>());
            menuMap.put(menuPageInfo.getId(), menuPageInfo);
        });

        // 遍历菜单项，将每个菜单项放置到其父节点下面
        menuPageInfoList.forEach(menuPageInfo -> {
            MenuPageInfo parentMenu = menuMap.get(menuPageInfo.getParentId());
            if (parentMenu != null) {
                parentMenu.getChildren().add(menuPageInfo);
            } else {
                // 如果父节点不存在，将其放置到根节点下面
                tree.add(menuPageInfo);
            }
        });

        return tree;
    }

}
