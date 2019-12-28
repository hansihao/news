package edu.nciae.system.controller;

import edu.nciae.common.annotation.LoginUser;
import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.system.domain.SysMenu;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限
 */
@RestController
@RequestMapping("menu")
public class SysMenuController extends BaseController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单权限
     *
     * @param sysUser
     * @return
     */
    @GetMapping("user")
    public List<SysMenu> user(@LoginUser SysUser sysUser) {
        List<SysMenu> menus = sysMenuService.selectMenusByUser(sysUser);
        return menus;
    }

    /**
     * 根据用户id查询对应权限
     *
     * @param userId
     * @return
     */
    @GetMapping("perms/{userId}")
    public Set<String> perms(@PathVariable("userId") Integer userId) {
        return sysMenuService.selectPermsByUserId(userId);
    }

    /**
     * 查询菜单权限列表
     */
    @HasPermissions("system:menu:view")
    @GetMapping("list")
    public R list(SysMenu sysMenu) {
        return result(sysMenuService.selectMenuList(sysMenu));
    }

    /**
     * 新增保存菜单权限
     */
    @PostMapping("save")
    @OperLog(title = "菜单管理", businessType = BusinessType.INSERT)
    public R addSave(@RequestBody SysMenu sysMenu) {
        int i = sysMenuService.insertMenu(sysMenu);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@RequestBody SysMenu sysMenu) {
        int i = sysMenuService.updateMenu(sysMenu);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除菜单权限
     */
    @OperLog(title = "菜单管理", businessType = BusinessType.DELETE)
    @PostMapping("remove/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId) {
        int i = sysMenuService.deleteMenuById(menuId);
        return i > 0 ? R.ok() : R.error();
    }
}
