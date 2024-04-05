package ink.usr.admin.service;

import ink.usr.admin.dao.SysRoleDao;
import ink.usr.common.interfaces.admin.ISysRoleService;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysRoleService")
public class SysRoleService implements ISysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 查询角色信息
     */
    public SysRoleModel selectRoleInfo(Long roleId) {
        return sysRoleDao.selectRoleById(Ds.W, roleId);
    }


    /**
     * 查询角色列表
     *
     * @param sysRoleModel 角色信息
     * @return 角色列表
     */
    public List<SysRoleModel> selectSysRoleList(SysRoleModel sysRoleModel) {
        return sysRoleDao.selectSysRoleList(Ds.W, sysRoleModel);
    }

    /**
     * 添加角色信息
     *
     * @param sysRoleModel 角色信息
     */
    public int insertSysRole(SysRoleModel sysRoleModel) {
        // 查询默认角色
        SysRoleModel defaultRole = sysRoleDao.selectRoleById(Ds.W, 3L);
        if (defaultRole == null) {
            throw new RuntimeException("添加失败，默认角色不存在！");
        }
        // 设置默认角色的权限
        sysRoleModel.setRoleMenuIds(defaultRole.getRoleMenuIds());
        return sysRoleDao.insertSysRole(Ds.W, sysRoleModel);
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleModel 角色信息
     */
    public int updateSysRole(SysRoleModel sysRoleModel) {
        return sysRoleDao.updateSysRole(Ds.W, sysRoleModel);
    }

    /**
     * 修改角色权限信息
     *
     * @param sysRoleModel 角色信息
     */
    public int updateSysRolePerm(SysRoleModel sysRoleModel) {
        return sysRoleDao.updateSysRolePerm(Ds.W, sysRoleModel);
    }

    /**
     * 逻辑删除角色信息
     *
     * @param roleId 角色ID
     */
    public int deleteSysRoleLogic(Long roleId) {
        int resCount = sysRoleDao.deleteSysRoleLogicById(Ds.W, roleId);
        if (resCount > 0) {
            // 删除角色，设置拥有此角色的用户角色为默认角色
            sysRoleDao.updateUserRoleToDefault(Ds.W, roleId);
        }
        return resCount;
    }

    /**
     * 恢复删除的角色信息
     *
     * @param roleId 角色ID
     */
    public int recoverSysRole(Long roleId) {
        return sysRoleDao.recoverSysRoleById(Ds.W, roleId);
    }

    /**
     * 物理删除角色信息
     *
     * @param roleId 角色ID
     */
    public int deleteSysRolePhysics(Long roleId) {
        return sysRoleDao.deleteSysRoleById(Ds.W, roleId);
    }

}
