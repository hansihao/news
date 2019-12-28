package edu.nciae.system.controller;

import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.system.domain.SysRole;
import edu.nciae.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 角色提供者
 */
@RestController
@RequestMapping("role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    @GetMapping("all")
    public R all() {
        return R.ok().put("rows", sysRoleService.selectRoleAll());
    }

    /**
     * 查询角色列表
     */
    @GetMapping("list")
    public R list(SysRole sysRole) {
        startPage();
        return result(sysRoleService.selectRoleList(sysRole));
    }

    /**
     * 修改保存角色
     */
    @OperLog(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("status")
    public R status(@RequestBody SysRole sysRole) {
        int i = sysRoleService.changeStatus(sysRole);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除角色
     *
     * @throws Exception
     */
    @OperLog(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        int i = sysRoleService.deleteRoleByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }
}
