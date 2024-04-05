package ink.usr.admin.mapper;

import ink.usr.common.model.mysql.SysRoleModel;

import java.util.List;

public interface SysRoleMapper {

    /**
     * 查询角色信息
     *
     * @param roleId 角色ID
     * @return 角色信息
     */
    SysRoleModel selectRoleById(Long roleId);

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
     * 更新用户角色为默认角色
     *
     * @param roleId 角色ID 用户的角色ID，拥有此角色的用户角色将被更新为默认角色
     */
    void updateUserRoleToDefault(Long roleId);

    /**
     * 逻辑删除角色信息
     *
     * @param roleId 角色ID
     */
    int deleteSysRoleLogicById(Long roleId);

    /**
     * 恢复删除的角色
     *
     * @param roleId 角色ID
     */
    int recoverSysRoleById(Long roleId);

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     */
    int deleteSysRoleById(Long roleId);

}
