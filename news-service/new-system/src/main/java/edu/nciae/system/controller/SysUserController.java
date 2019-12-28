package edu.nciae.system.controller;

import edu.nciae.common.annotation.LoginUser;
import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.constant.UserConstants;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.common.utils.DateUtils;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.service.SysMenuService;
import edu.nciae.system.service.SysUserService;
import edu.nciae.system.util.PasswordUtil;
import edu.nciae.system.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户提供者
 */
@RestController
@RequestMapping("user")
public class SysUserController extends BaseController {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 根据id查询用户
     *
     * @param userId
     * @return
     */
    @GetMapping("get/{userId}")
    public SysUser get(@PathVariable("userId") Integer userId) {
        return sysUserService.selectUserById(userId);
    }

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    @GetMapping("find/{username}")
    public SysUser findUserByUsername(@PathVariable("username") String username) {
        return sysUserService.selectUserByLoginName(username);
    }

    /**
     * 记录登陆信息
     *
     * @param sysUser
     * @return
     */
    @PostMapping("update/login")
    public R updateLoginRecord(@RequestBody SysUser sysUser) {
        sysUserService.updateUser(sysUser);
        return R.ok();
    }

    /**
     * 查询用户详细信息
     *
     * @param sysUser
     * @return
     */
    @GetMapping("info")
    public SysUser info(@LoginUser SysUser sysUser) {
        sysUser.setButtons(sysMenuService.selectPermsByUserId(sysUser.getUserId()));
        return sysUser;
    }

    /**
     * 修改用户信息
     *
     * @param sysUser
     * @return
     * @author zmr
     */
    @HasPermissions("system:user:edit")
    @PutMapping("update/info")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public R updateInfo(@LoginUser SysUser sysUser, @RequestBody UserVo uservo) {
        int i = sysUserService.updateUserInfo(sysUser, uservo);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 查询用户列表
     */
    @GetMapping("list")
    public R list(SysUser sysUser) {
        startPage();
        return result(sysUserService.selectUserList(sysUser));
    }

    /**
     * 新增保存用户
     */
    @HasPermissions("system:user:add")
    @PostMapping("save")
    @OperLog(title = "用户管理", businessType = BusinessType.INSERT)
    public R addSave(@RequestBody SysUser sysUser) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(sysUserService.checkLoginNameUnique(sysUser.getLoginName()))) {
            return R.error("新增用户'" + sysUser.getLoginName() + "'失败，登录账号已存在");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            return R.error("新增用户'" + sysUser.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return R.error("新增用户'" + sysUser.getLoginName() + "'失败，邮箱账号已存在");
        }
        sysUser.setPassword(PasswordUtil.encryptPassword(sysUser.getLoginName(), sysUser.getPassword(), sysUser.getSalt()));
        sysUser.setCreateBy(getLoginName());
        sysUser.setCreateTime(DateUtils.getNowDate());
        int i = sysUserService.insertUser(sysUser);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存用户
     */
    @HasPermissions("system:user:edit")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("update")
    public R editSave(@LoginUser SysUser user, @RequestBody SysUser sysUser) {
        if (null != sysUser.getUserId() && sysUser.getUserId() == 1) {
            return R.error("不允许修改超级管理员用户");
        } else if (UserConstants.USER_PHONE_NOT_UNIQUE.equals(sysUserService.checkPhoneUnique(sysUser))) {
            return R.error("修改用户'" + sysUser.getLoginName() + "'失败，手机号码已存在");
        } else if (UserConstants.USER_EMAIL_NOT_UNIQUE.equals(sysUserService.checkEmailUnique(sysUser))) {
            return R.error("修改用户'" + sysUser.getLoginName() + "'失败，邮箱账号已存在");
        }
        int i = sysUserService.updateUser(user, sysUser);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改状态
     *
     * @param sysUser
     * @return
     */
    @HasPermissions("system:user:edit")
    @PostMapping("status")
    @OperLog(title = "用户管理", businessType = BusinessType.UPDATE)
    public R status(@RequestBody SysUser sysUser) {
        int i = sysUserService.changeStatus(sysUser);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除用户
     *
     * @throws Exception
     */
    @HasPermissions("system:user:remove")
    @OperLog(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        int i = sysUserService.deleteUserByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }

    @HasPermissions("system:user:resetPwd")
    @OperLog(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    public R resetPwdSave(@RequestBody SysUser user) {
        user.setPassword(PasswordUtil.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        int i = sysUserService.resetUserPwd(user);
        return i > 0 ? R.ok() : R.error();
    }

}
