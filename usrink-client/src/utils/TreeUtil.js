/**
 * 构建菜单树形结构
 * @param menuList 扁平化菜单项
 */
const buildMenuTree = (menuList) => {
    let menuMap = {}; // 用于存储menuId对应的菜单项
    let tree = []; // 存储最终的树形结构

    // 首先将每个菜单项存储到menuMap中
    menuList.forEach(menu => {
        menu.children = []; // 初始化子节点数组
        menuMap[menu.menuId] = menu;
    });

    // 遍历菜单项，将每个菜单项放置到其父节点下面
    menuList.forEach(menu => {
        let parentMenu = menuMap[menu.parentMenuId];
        if (parentMenu) {
            parentMenu.children.push(menu);
        } else {
            // 如果父节点不存在，将其放置到根节点下面
            tree.push(menu);
        }
    });

    return tree;
}

/**
 * 把树形数据转换成扁平化数据
 * @param tree 树形数据
 */
const treeToFlat = (tree) => {
    let flat = [];
    tree.forEach(node => {
        flat.push(node);
        if (node.children && node.children.length > 0) {
            flat = flat.concat(treeToFlat(node.children));
        }
    });
    return flat;
}

export default {buildMenuTree, treeToFlat}