package ink.usr.common.interfaces.admin;

import ink.usr.common.model.mysql.SysRoleModel;

import java.util.List;

public interface ISysRoleService {

    /**
     * 查询角色信息
     *
     * @param roleId 角色ID
     */
    SysRoleModel selectRoleInfo(Long roleId);

    /**
     * 查询角色列表
     *
     * @param sysRoleModel 角色信息
     * @return 角色列表
     */
    List<SysRoleModel> selectSysRoleList(SysRoleModel sysRoleModel);

    /**
     * 添加角色信息
     *
     * @param sysRoleModel 角色信息
     */
    int insertSysRole(SysRoleModel sysRoleModel);

    /**
     * 修改角色信息
     *
     * @param sysRoleModel 角色信息
     */
    int updateSysRole(SysRoleModel sysRoleModel);

    /**
     * 修改角色权限信息
     *
     * @param sysRoleModel 角色信息
     */
    int updateSysRolePerm(SysRoleModel sysRoleModel);

    /**
     * 逻辑删除角色信息
     *
     * @param roleId 角色ID
     */
    int deleteSysRoleLogic(Long roleId);

    /**
     * 恢复删除的角色信息
     */
    int recoverSysRole(Long roleId);

    /**
     * 物理删除角色信息
     *
     * @param roleId 角色ID
     */
    int deleteSysRolePhysics(Long roleId);

}
