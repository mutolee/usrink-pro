package ink.usr.admin.mapper;

import ink.usr.common.model.mysql.SysUserModel;

import java.util.List;

public interface SysUserMapper {

    /**
     * 用户登录，查询用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    SysUserModel selectSysUserForLogin(String userName);

    /**
     * 查询用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    SysUserModel selectSysUserById(Long userId);

    /**
     * 查询用户列表
     *
     * @param sysUserModel 用户信息
     */
    List<SysUserModel> selectSysUserList(SysUserModel sysUserModel);

    /**
     * 添加用户信息
     *
     * @param sysUserModel 用户信息
     */
    int insertSysUser(SysUserModel sysUserModel);

    /**
     * 检查用户名是否存在
     */
    SysUserModel checkUserNameUnique(String userName);

    /**
     * 修改用户信息
     *
     * @param sysUserModel 用户信息
     */
    int updateSysUser(SysUserModel sysUserModel);

    /**
     * 修改用户角色
     */
    int updateSysUserRole(SysUserModel sysUserModel);

    /**
     * 更新用户密码
     */
    int updateSysUserPassword(SysUserModel sysUserModel);

    /**
     * 逻辑删除用户
     *
     * @param userId 用户ID
     */
    int deleteSysUserLogicById(Long userId);

    /**
     * 恢复删除的用户
     *
     * @param userId 用户ID
     */
    int recoverSysUserById(Long userId);

    /**
     * 删除用户信息
     *
     * @param userId 用户ID
     */
    int deleteSysUserById(Long userId);

}
