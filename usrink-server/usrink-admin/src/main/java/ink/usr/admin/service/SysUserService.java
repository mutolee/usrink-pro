package ink.usr.admin.service;

import ink.usr.admin.dao.SysRoleDao;
import ink.usr.admin.dao.SysUserDao;
import ink.usr.common.core.constants.Constants;
import ink.usr.common.core.exception.WarningException;
import ink.usr.common.core.exception.base.BusinessException;
import ink.usr.common.core.utils.Md5Util;
import ink.usr.common.domain.admin.UpdatePwdForm;
import ink.usr.common.interfaces.admin.ISysUserService;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.common.model.mysql.SysUserModel;
import ink.usr.framework.mysql.enums.Ds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service(value = "sysUserService")
public class SysUserService implements ISysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;

    /**
     * 查询系统用户资料
     */
    public SysUserModel selectSysUserInfo(Long userId) {
        SysUserModel sysUserModel = sysUserDao.selectSysUserById(Ds.W, userId);
        if (sysUserModel == null) {
            throw new BusinessException("用户不存在");
        }
        if (sysUserModel.getStatus() != 0) {
            throw new BusinessException("用户已被禁用");
        }
        return sysUserModel;
    }

    /**
     * 查询用户列表
     *
     * @param sysUserModel 用户信息
     * @return 用户列表
     */
    public List<SysUserModel> selectSysUserList(SysUserModel sysUserModel) {
        return sysUserDao.selectSysUserList(Ds.W, sysUserModel);
    }

    /**
     * 添加用户信息
     *
     * @param sysUserModel 用户信息
     */
    public int insertSysUser(SysUserModel sysUserModel) {
        // 检查用户名是否存在
        SysUserModel existSysUser = sysUserDao.checkUserNameUnique(Ds.W, sysUserModel.getUserName());
        if (existSysUser != null) {
            throw new BusinessException("用户名已存在");
        }
        // 设置用户默认角色
        sysUserModel.setUserRoleId(2L);
        // 密码加密
        sysUserModel.setUserPassword(Md5Util.md5(sysUserModel.getUserPassword() + Constants.SALT));
        return sysUserDao.insertSysUser(Ds.W, sysUserModel);
    }

    /**
     * 检查用户名是否存在
     */
    public SysUserModel checkUserNameUnique(String userName) {
        return sysUserDao.checkUserNameUnique(Ds.W, userName);
    }

    /**
     * 修改用户信息
     *
     * @param sysUserModel 用户信息
     */
    public int updateSysUser(SysUserModel sysUserModel) {
        return sysUserDao.updateSysUser(Ds.W, sysUserModel);
    }

    /**
     * 修改用户角色
     */
    public int updateSysUserRole(SysUserModel sysUserModel) {
        // 如果角色为空，设置为默认角色
        if (sysUserModel.getUserRoleId() == null) {
            sysUserModel.setUserRoleId(2L);
        }
        // 如果角色不存在，设置为默认角色
        SysRoleModel sysRoleModel = sysRoleDao.selectRoleById(Ds.W, sysUserModel.getUserRoleId());
        if (sysRoleModel == null || sysRoleModel.getStatus() == -1) {
            sysUserModel.setUserRoleId(2L);
        }
        return sysUserDao.updateSysUserRole(Ds.W, sysUserModel);
    }

    /**
     * 更新用户密码
     */
    public int updateSysUserPassword(SysUserModel sysUserModel) {
        // 密码加密
        String password = Md5Util.md5(Constants.DEFAULT_PASSWORD);
        // 密码加盐
        sysUserModel.setUserPassword(Md5Util.md5(password + Constants.SALT));
        return sysUserDao.updateSysUserPassword(Ds.W, sysUserModel);
    }

    /**
     * 逻辑删除用户
     */
    public int deleteSysUserLogic(Long userId) {
        // 删除用户，把用户角色改为默认角色
        SysUserModel sysUserModel = SysUserModel.builder()
                .userId(userId)
                .userRoleId(2L)
                .build();
        int resCount = sysUserDao.updateSysUserRole(Ds.W, sysUserModel);
        if (resCount <= 0) {
            log.error("删除用户，把用户角色改为默认角色失败，userId:{}", userId);
        }

        return sysUserDao.deleteSysUserLogicById(Ds.W, userId);
    }

    /**
     * 恢复删除的用户
     */
    public int recoverSysUser(Long userId) {
        return sysUserDao.recoverSysUserById(Ds.W, userId);
    }

    /**
     * 物理删除用户信息
     *
     * @param userId 用户ID
     */
    public int deleteSysUserPhysics(Long userId) {
        return sysUserDao.deleteSysUserById(Ds.W, userId);
    }

    /**
     * 修改个人密码
     */
    public boolean updateMyPwd(Long userId, UpdatePwdForm updatePwdForm) {
        SysUserModel sysUserModel = sysUserDao.selectSysUserById(Ds.W, userId);
        if (sysUserModel == null) {
            throw new BusinessException("用户不存在！");
        }
        String oldPwd = Md5Util.md5(updatePwdForm.getOldPwd() + Constants.SALT);
        if (!sysUserModel.getUserPassword().equals(oldPwd)) {
            throw new WarningException("原密码错误！");
        }
        String newPwd = Md5Util.md5(updatePwdForm.getNewPwd() + Constants.SALT);
        int count = sysUserDao.updateSysUserPassword(Ds.W, SysUserModel.builder()
                .userId(userId)
                .userPassword(newPwd)
                .build());
        return count > 0;
    }
}
