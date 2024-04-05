package ink.usr.admin.dao;

import ink.usr.admin.mapper.SysMenuMapper;
import ink.usr.common.model.mysql.SysMenuModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysMenuDao")
public class SysMenuDao {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    /**
     * 查询菜单列表
     *
     * @return 菜单列表
     */
    public List<SysMenuModel> selectSysMenuList(Ds rw, SysMenuModel sysMenuModel) {
        return sysMenuMapper.selectSysMenuList(sysMenuModel);
    }


    /**
     * 添加菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    public int insertSysMenu(Ds rw, SysMenuModel sysMenuModel) {
        return sysMenuMapper.insertSysMenu(sysMenuModel);
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    public int updateSysMenu(Ds rw, SysMenuModel sysMenuModel) {
        return sysMenuMapper.updateSysMenu(sysMenuModel);
    }

    /**
     * 逻辑删除菜单信息
     *
     * @param menuId 菜单ID
     */
    public int deleteSysMenuLogicById(Ds rw, Long menuId) {
        return sysMenuMapper.deleteSysMenuLogicById(menuId);
    }

    /**
     * 恢复删除的菜单信息
     *
     * @param menuId 菜单ID
     */
    public int recoverSysMenuById(Ds rw, Long menuId) {
        return sysMenuMapper.recoverSysMenuById(menuId);
    }

    /**
     * 物理删除菜单
     *
     * @param menuId 菜单ID
     */
    public int deleteSysMenuById(Ds rw, Long menuId) {
        return sysMenuMapper.deleteSysMenuById(menuId);
    }

}
