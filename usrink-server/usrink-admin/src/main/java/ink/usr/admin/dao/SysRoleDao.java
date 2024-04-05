package ink.usr.admin.dao;

import ink.usr.admin.mapper.SysRoleMapper;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysRoleDao")
public class SysRoleDao {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    /**
     * 查询角色信息
     *
     * @param rw     数据源
     * @param roleId 角色ID
     * @return 角色信息
     */
    public SysRoleModel selectRoleById(Ds rw, Long roleId) {
        return sysRoleMapper.selectRoleById(roleId);
    }

    /**
     * 查询角色列表
     *
     * @param sysRoleModel 角色信息
     * @return 角色列表
     */
    public List<SysRoleModel> selectSysRoleList(Ds rw, SysRoleModel sysRoleModel) {
        return sysRoleMapper.selectSysRoleList(sysRoleModel);
    }

    /**
     * 添加角色信息
     *
     * @param sysRoleModel 角色信息
     */
    public int insertSysRole(Ds rw, SysRoleModel sysRoleModel) {
        return sysRoleMapper.insertSysRole(sysRoleModel);
    }

    /**
     * 修改角色信息
     *
     * @param sysRoleModel 角色信息
     */
    public int updateSysRole(Ds rw, SysRoleModel sysRoleModel) {
        return sysRoleMapper.updateSysRole(sysRoleModel);
    }

    /**
     * 修改角色权限信息
     *
     * @param sysRoleModel 角色信息
     */
    public int updateSysRolePerm(Ds rw, SysRoleModel sysRoleModel) {
        return sysRoleMapper.updateSysRolePerm(sysRoleModel);
    }

    /**
     * 更新用户角色为默认角色
     *
     * @param roleId 角色ID 用户的角色ID，拥有此角色的用户角色将被更新为默认角色
     */
    public void updateUserRoleToDefault(Ds rw, Long roleId) {
        sysRoleMapper.updateUserRoleToDefault(roleId);
    }

    /**
     * 逻辑删除角色信息
     *
     * @param roleId 角色ID
     */
    public int deleteSysRoleLogicById(Ds rw, Long roleId) {
        return sysRoleMapper.deleteSysRoleLogicById(roleId);
    }

    /**
     * 恢复删除的角色
     *
     * @param roleId 角色ID
     */
    public int recoverSysRoleById(Ds rw, Long roleId) {
        return sysRoleMapper.recoverSysRoleById(roleId);
    }

    /**
     * 删除角色信息
     *
     * @param roleId 角色ID
     */
    public int deleteSysRoleById(Ds rw, Long roleId) {
        return sysRoleMapper.deleteSysRoleById(roleId);
    }

}
