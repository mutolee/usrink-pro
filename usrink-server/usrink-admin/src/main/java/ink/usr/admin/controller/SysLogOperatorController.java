package ink.usr.admin.controller;

import com.github.pagehelper.Page;
import ink.usr.common.core.annotation.Log;
import ink.usr.common.core.domain.Dict;
import ink.usr.common.core.domain.Res;
import ink.usr.common.core.utils.PageUtil;
import ink.usr.common.domain.admin.SysLogOperatorForm;
import ink.usr.common.interfaces.admin.ISysLogOperatorService;
import ink.usr.common.model.mysql.SysLogOperatorModel;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sysLogOperator")
public class SysLogOperatorController {

    @Autowired
    private ISysLogOperatorService sysLogOperatorService;

    /**
     * 查询系统操作日志
     *
     * @param sysLogOperatorForm 系统操作日志
     * @return 系统操作日志
     */
    @Log("查询系统操作日志列表")
    @RequestMapping(value = "/selectSysLogOperatorList")
    @RequiresPermissions("sys:log:operator:list")
    public Res selectSysLogOperatorList(SysLogOperatorForm sysLogOperatorForm) {
        // 开启分页
        Page<Object> pages = PageUtil.startPage();
        List<SysLogOperatorModel> sysLogOperatorModels = sysLogOperatorService.selectSysLogOperatorList(sysLogOperatorForm);
        Dict result = Dict.create()
                .set("list", sysLogOperatorModels)
                .set("total", pages.getTotal());
        return Res.success(result);
    }

    /**
     * 查询操作日志详细信息
     *
     * @param logId 日志ID
     */
    @Log("查询操作日志详细信息")
    @RequestMapping(value = "/selectSysLogOperatorInfo")
    @RequiresPermissions("sys:log:operator:info")
    public Res selectSysLogOperatorInfo(Long logId) {
        SysLogOperatorModel sysLogOperatorModel = sysLogOperatorService.selectSysLogOperatorInfo(logId);
        return Res.success(sysLogOperatorModel);
    }

    /**
     * 物理删除系统操作日志
     *
     * @param logId 日志ID
     */
    @Log("删除系统操作日志")
    @RequestMapping(value = "/deleteSysLogOperator")
    @RequiresPermissions("sys:log:operator:delete")
    public Res deleteSysLogOperator(Long logId) {
        int result = sysLogOperatorService.deleteSysLogOperator(logId);
        return Res.success(result);
    }

    /**
     * 根据时间删除操作日志
     */
    @Log("根据时间段删除操作日志")
    @RequestMapping(value = "/deleteSysLogOperatorByTime")
    @RequiresPermissions("sys:log:operator:clear")
    public Res deleteSysLogOperatorByTime(SysLogOperatorForm sysLogOperatorForm) {
        int result = sysLogOperatorService.deleteSysLogOperatorByTime(sysLogOperatorForm);
        return Res.success(result);
    }

    /**
     * 清空操作日志
     */
    @Log("清空所有操作日志")
    @RequestMapping(value = "/cleanSysLogOperator")
    @RequiresPermissions("sys:log:operator:clearAll")
    public Res cleanSysLogOperator() {
        SysLogOperatorForm sysLogOperatorForm = new SysLogOperatorForm();
        int result = sysLogOperatorService.deleteSysLogOperatorByTime(sysLogOperatorForm);
        return Res.success(result);
    }
}
