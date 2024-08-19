package ink.usr.admin.controller;

import com.github.pagehelper.Page;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.core.utils.PageUtil;
import ink.usr.common.domain.admin.SysLogLoginForm;
import ink.usr.common.interfaces.admin.ISysLogLoginService;
import ink.usr.common.model.mysql.SysLogLoginModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sysLogLogin")
public class SysLogLoginController {

    @Autowired
    private ISysLogLoginService sysLogLoginService;

    /**
     * 查询系统登录日志
     *
     * @param sysLogLoginForm 系统登录日志
     * @return 系统登录日志
     */
    @Log("查询系统登录日志列表")
    @RequestMapping(value = "/selectSysLogLoginList")
    @RequiresPermissions("sys:log:login:list")
    public Res selectSysLogLoginList(SysLogLoginForm sysLogLoginForm) {
        // 开启分页
        Page<Object> pages = PageUtil.startPage();
        List<SysLogLoginModel> sysLogLoginModels = sysLogLoginService.selectSysLogLoginList(sysLogLoginForm);
        Dict result = Dict.create()
                .set("list", sysLogLoginModels)
                .set("total", pages.getTotal());
        return Res.success(result);
    }

    /**
     * 查询登录日志详细信息
     *
     * @param logId 日志ID
     */
    @Log("查询登录日志详细信息")
    @RequestMapping(value = "/selectSysLogLoginInfo")
    @RequiresPermissions("sys:log:login:info")
    public Res selectSysLogLoginInfo(Long logId) {
        SysLogLoginModel sysLogLoginModel = sysLogLoginService.selectSysLogLoginInfo(logId);
        return Res.success(sysLogLoginModel);
    }

    /**
     * 物理删除系统登录日志
     *
     * @param logId 日志ID
     */
    @Log("删除系统登录日志")
    @RequestMapping(value = "/deleteSysLogLogin")
    @RequiresPermissions("sys:log:login:delete")
    public Res deleteSysLogLogin(Long logId) {
        int result = sysLogLoginService.deleteSysLogLogin(logId);
        return Res.success(result);
    }

    /**
     * 根据时间删除登录日志
     */
    @Log("根据时间段删除登录日志")
    @RequestMapping(value = "/deleteSysLogLoginByTime")
    @RequiresPermissions("sys:log:login:clear")
    public Res deleteSysLogLoginByTime(SysLogLoginForm sysLogLoginForm) {
        int result = sysLogLoginService.deleteSysLogLoginByTime(sysLogLoginForm);
        return Res.success(result);
    }

    /**
     * 清空登录日志
     */
    @Log("清空所有登录日志")
    @RequestMapping(value = "/cleanSysLogLogin")
    @RequiresPermissions("sys:log:login:clearAll")
    public Res cleanSysLogLogin() {
        SysLogLoginForm sysLogLoginForm = new SysLogLoginForm();
        int result = sysLogLoginService.deleteSysLogLoginByTime(sysLogLoginForm);
        return Res.success(result);
    }
}
