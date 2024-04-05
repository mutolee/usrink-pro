package ink.usr.admin.dao;

import ink.usr.admin.mapper.SysUserMapper;
import ink.usr.common.model.mysql.SysUserModel;
import ink.usr.framework.mysql.enums.Ds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value = "sysUserDao")
public class SysUserDao {

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 用户登录，查询用户信息
     *
     * @param rw       数据源
     * @param userName 用户名
     * @return 用户信息
     */
    public SysUserModel selectSysUserForLogin(Ds rw, String userName) {
        return sysUserMapper.selectSysUserForLogin(userName);
    }

    /**
     * 查询用户信息
     *
     * @param rw     数据源
     * @param userId 用户ID
     * @return 用户信息
     */
    public SysUserModel selectSysUserById(Ds rw, Long userId) {
        return sysUserMapper.selectSysUserById(userId);
    }

    /**
     * 查询用户列表
     *
     * @param sysUserModel 用户信息
     */
    public List<SysUserModel> selectSysUserList(Ds rw, SysUserModel sysUserModel) {
        return sysUserMapper.selectSysUserList(sysUserModel);
    }

    /**
     * 添加用户信息
     *
     * @param sysUserModel 用户信息
     */
    public int insertSysUser(Ds rw, SysUserModel sysUserModel) {
        return sysUserMapper.insertSysUser(sysUserModel);
    }

    /**
     * 检查用户名是否存在
     */
    public SysUserModel checkUserNameUnique(Ds rw, String userName) {
        return sysUserMapper.checkUserNameUnique(userName);
    }

    /**
     * 修改用户信息
     *
     * @param sysUserModel 用户信息
     */
    public int updateSysUser(Ds rw, SysUserModel sysUserModel) {
        return sysUserMapper.updateSysUser(sysUserModel);
    }

    /**
     * 修改用户角色
     */
    public int updateSysUserRole(Ds rw, SysUserModel sysUserModel) {
        return sysUserMapper.updateSysUserRole(sysUserModel);
    }

    /**
     * 更新用户密码
     */
    public int updateSysUserPassword(Ds rw, SysUserModel sysUserModel) {
        return sysUserMapper.updateSysUserPassword(sysUserModel);
    }

    /**
     * 逻辑删除用户
     *
     * @param userId 用户ID
     */
    public int deleteSysUserLogicById(Ds rw, Long userId) {
        return sysUserMapper.deleteSysUserLogicById(userId);
    }

    /**
     * 恢复删除的用户
     *
     * @param userId 用户ID
     */
    public int recoverSysUserById(Ds rw, Long userId) {
        return sysUserMapper.recoverSysUserById(userId);
    }

    /**
     * 物理删除用户信息
     *
     * @param userId 用户ID
     */
    public int deleteSysUserById(Ds rw, Long userId) {
        return sysUserMapper.deleteSysUserById(userId);
    }


}
