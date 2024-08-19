package ink.usr.admin.controller;

import com.github.pagehelper.Page;
import ink.usr.admin.service.ShiroService;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.constants.Constants;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.core.exception.base.BusinessException;
import ink.usr.common.core.utils.*;
import ink.usr.common.domain.admin.SysUserInfo;
import ink.usr.common.domain.admin.UpdatePwdForm;
import ink.usr.common.interfaces.admin.ISysRoleService;
import ink.usr.common.interfaces.admin.ISysUserService;
import ink.usr.common.model.mysql.SysRoleModel;
import ink.usr.common.model.mysql.SysUserModel;
import ink.usr.framework.shiro.utils.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController {

    @Autowired
    private ShiroService shiroService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

    // 文件上传路径
    @Value("${file.upload.path}")
    private String uploadPath;


    /**
     * 查询用户列表
     *
     * @param sysUserModel 用户信息
     */
    @Log("查询用户列表")
    @RequestMapping(value = "/selectSysUserList")
    @RequiresPermissions("sys:user:list")
    public Res selectSysUserList(SysUserModel sysUserModel) {
        // 开启分页
        Page<Object> pages = PageUtil.startPage();
        List<SysUserModel> sysUserModels = sysUserService.selectSysUserList(sysUserModel);

        // 前端需要的用户列表
        List<SysUserInfo> sysUserInfos = new ArrayList<>();
        if (CollUtil.isNotEmpty(sysUserModels)) {
            // 查询角色列表
            List<SysRoleModel> sysRoleModels = sysRoleService.selectSysRoleList(SysRoleModel.builder().status(0).build());

            // 设置用户角色名称
            sysUserModels.forEach(model -> {
                SysUserInfo sysUserInfo = ObjectUtil.copyProperties(model, SysUserInfo.class);
                SysRoleModel sysRoleModel = sysRoleModels.stream().filter(role -> role.getRoleId().equals(sysUserInfo.getUserRoleId())).findFirst().orElse(null);
                if (sysRoleModel != null) {
                    sysUserInfo.setRoleName(sysRoleModel.getRoleName());
                }
                sysUserInfos.add(sysUserInfo);
            });
        }

        Dict result = Dict.create()
                .set("list", sysUserInfos)
                .set("total", pages.getTotal());
        return Res.success(result);
    }

    /**
     * 检查用户名是否存在
     */
    @Log("检查用户名是否存在")
    @RequestMapping(value = "/checkUserNameUnique")
    public Res checkUserNameUnique(String userName) {
        SysUserModel sysUserModel = sysUserService.checkUserNameUnique(userName);
        Dict result = Dict.create();
        result.set("valid", sysUserModel == null);
        return Res.success().setData(result);
    }

    /**
     * 添加用户信息
     */
    @Log("添加用户信息")
    @RequestMapping(value = "/addSysUser")
    @RequiresPermissions("sys:user:add")
    public Res addSysUser(SysUserModel sysUserModel) {
        int resCount = sysUserService.insertSysUser(sysUserModel);
        if (resCount <= 0) {
            return Res.error("添加用户失败");
        }
        return Res.success();
    }

    /**
     * 更新用户信息
     */
    @Log("修改用户信息")
    @RequestMapping(value = "/updateSysUser")
    @RequiresPermissions("sys:user:update")
    public Res updateSysUser(SysUserModel sysUserModel) {
        int resCount = sysUserService.updateSysUser(sysUserModel);
        if (resCount <= 0) {
            return Res.error("更新用户失败");
        }

        return Res.success();
    }

    /**
     * 更新我的资料信息
     */
    @Log("个人中心，修改用户信息")
    @RequestMapping(value = "/updateMyProfile")
    @RequiresPermissions("user:profile:update")
    public Res updateMyProfile(SysUserModel sysUserModel) {
        int resCount = sysUserService.updateSysUser(sysUserModel);
        if (resCount <= 0) {
            return Res.error("更新用户失败");
        }

        return Res.success();
    }

    /**
     * 更新我的密码
     */
    @Log("个人中心，修改密码")
    @RequestMapping(value = "/updateMyPwd")
    @RequiresPermissions("user:profile:restPwd")
    public Res updateMyPwd(UpdatePwdForm updatePwdForm) {
        Long userId = ShiroUtil.getShiroUserInfo().getUserId();
        boolean isSuccess = sysUserService.updateMyPwd(userId, updatePwdForm);
        if (!isSuccess) {
            return Res.error("个人中心，修改密码失败");
        }

        return Res.success();
    }

    /**
     * 更新我的头像
     */
    @Log("个人中心，修改头像")
    @RequestMapping(value = "/updateMyAvatar", method = RequestMethod.POST)
    @RequiresPermissions("user:profile:updateAvatar")
    public Res updateMyAvatar(String base64Image){
        if(!StringUtil.isEmpty(base64Image)){
            // 限制头像大小，不能超过100KB
            if (base64Image.length() > 102400) {
                return Res.error("头像大小不能超过100KB");
            }
        }
        Long userId = ShiroUtil.getShiroUserInfo().getUserId();
        String fileName = "avatar_" + userId + ".jpg";
        String uploadDir = uploadPath + Constants.USER_AVATAR_DIR;
        try {
            String uploadResult = UploadUtil.uploadBase64Image(base64Image, uploadDir, fileName);
            log.info("用户({})头像上传成功，路径：{}", userId, uploadResult);
        } catch (IOException e) {
            throw new BusinessException(StringUtil.format("用户({})头像上传失败", userId));
        }

        // 更新用户头像
        SysUserModel sysUserModel = new SysUserModel();
        sysUserModel.setUserId(userId);
        sysUserModel.setAvatar(Constants.USER_AVATAR_REQ_SUFFIX + fileName + "?t=" + System.currentTimeMillis());
        int resCount = sysUserService.updateSysUser(sysUserModel);

        if (resCount <= 0) {
            return Res.error(StringUtil.format("用户({})头像修改失败", userId));
        }

        Dict result = Dict.create();
        result.set("avatar", sysUserModel.getAvatar());
        return Res.success(result);
    }


    /**
     * 更新用户角色
     */
    @Log("分配用户角色")
    @RequestMapping(value = "/updateSysUserRole")
    @RequiresPermissions("sys:user:distribute:role")
    public Res updateSysUserRole(SysUserModel sysUserModel) {
        // 判断是否为超级管理员，超级管理员不允许修改角色
        if (sysUserModel.getUserId() == 1) {
            return Res.error("超级管理员不允许修改角色");
        }
        int resCount = sysUserService.updateSysUserRole(sysUserModel);
        if (resCount <= 0) {
            return Res.error("更新用户角色失败");
        }

        // 更新了用户角色，需要删除用户角色缓存
        shiroService.deleteUserRoleCacheByUserId(sysUserModel.getUserId());
        return Res.success();
    }

    /**
     * 重置用户密码
     */
    @Log("重置用户密码")
    @RequestMapping(value = "/resetUserPwd")
    @RequiresPermissions("sys:user:rest:pwd")
    public Res resetUserPwd(SysUserModel sysUserModel) {
        // 超级管理员密码，只有超级管理员本人可以重置
        if (sysUserModel.getUserId() == 1) {
            Long userId = ShiroUtil.getShiroUserInfo().getUserId();
            if (!userId.equals(1L)) {
                return Res.error("超级管理员密码只有超级管理员本人可以重置");
            }
        }

        int resCount = sysUserService.updateSysUserPassword(sysUserModel);
        if (resCount <= 0) {
            return Res.error("重置用户密码失败");
        }
        return Res.success();
    }

    /**
     * 逻辑删除用户信息
     */
    @Log("删除用户信息")
    @RequestMapping(value = "/deleteSysUserLogic")
    @RequiresPermissions("sys:user:deleteLogic")
    public Res deleteSysUserLogic(SysUserModel sysUserModel) {
        // 判断是否为超级管理员，超级管理员不允许删除
        if (sysUserModel.getUserId() == 1) {
            return Res.error("超级管理员不允许删除");
        }

        int resCount = sysUserService.deleteSysUserLogic(sysUserModel.getUserId());
        if (resCount <= 0) {
            return Res.error("删除用户失败");
        }

        // 删除了用户，需要删除用户角色缓存
        shiroService.deleteUserRoleCacheByUserId(sysUserModel.getUserId());

        return Res.success();
    }

    /**
     * 恢复删除的用户信息
     */
    @Log("恢复删除的用户信息")
    @RequestMapping(value = "/recoverSysUser")
    @RequiresPermissions("sys:user:deleteRecover")
    public Res recoverSysUser(SysUserModel sysUserModel) {
        int resCount = sysUserService.recoverSysUser(sysUserModel.getUserId());
        if (resCount <= 0) {
            return Res.error("恢复用户失败");
        }

        return Res.success();
    }

    /**
     * 物理删除用户信息
     */
    @Log("物理删除用户信息")
    @RequestMapping(value = "/deleteSysUserPhysics")
    @RequiresPermissions("sys:user:deletePhysics")
    public Res deleteSysUserPhysics(SysUserModel sysUserModel) {
        // 判断是否为超级管理员，超级管理员不允许删除
        if (sysUserModel.getUserId() == 1) {
            return Res.error("超级管理员不允许删除");
        }
        int resCount = sysUserService.deleteSysUserPhysics(sysUserModel.getUserId());
        if (resCount <= 0) {
            return Res.error("删除用户失败");
        }
        return Res.success();
    }

}
