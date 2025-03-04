package ink.usr.admin.service;

import ink.usr.admin.dao.SysMenuDao;
import ink.usr.admin.service.base.SysMenuBaseService;
import ink.usr.common.entity.admin.interfaces.ISysMenuService;
import ink.usr.common.entity.model.mysql.SysMenuModel;
import ink.usr.framework.mysql.entity.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysMenuService")
public class SysMenuService extends SysMenuBaseService implements ISysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 查询菜单列表
     */
    public List<SysMenuModel> selectMenuList(SysMenuModel sysMenuModel) {
        return sysMenuDao.selectSysMenuList(Ds.W, sysMenuModel);
    }

    /**
     * 添加菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    public int insertSysMenu(SysMenuModel sysMenuModel) {
        return sysMenuDao.insertSysMenu(Ds.W, sysMenuModel);
    }

    /**
     * 更新菜单信息
     *
     * @param sysMenuModel 菜单信息
     */
    public int updateSysMenu(SysMenuModel sysMenuModel) {
        return sysMenuDao.updateSysMenu(Ds.W, sysMenuModel);
    }

    /**
     * 逻辑删除菜单信息
     *
     * @param menuId 菜单ID
     */
    public int deleteSysMenuLogic(Long menuId) {
        return sysMenuDao.deleteSysMenuLogicById(Ds.W, menuId);
    }

    /**
     * 恢复删除的菜单信息
     *
     * @param menuId 菜单ID
     */
    public int recoverSysMenu(Long menuId) {
        return sysMenuDao.recoverSysMenuById(Ds.W, menuId);
    }

    /**
     * 删除菜单，物理删除，不可恢复
     *
     * @param menuId 菜单ID
     */
    public int deleteSysMenuPhysics(Long menuId) {
        return sysMenuDao.deleteSysMenuById(Ds.W, menuId);
    }


}
