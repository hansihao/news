package edu.nciae.system.service.impl;

import edu.nciae.common.constant.UserConstants;
import edu.nciae.common.core.text.Convert;
import edu.nciae.common.exception.BusinessException;
import edu.nciae.common.utils.DateUtils;
import edu.nciae.common.utils.StringUtils;
import edu.nciae.system.domain.SysUserRole;
import edu.nciae.system.mapper.SysUserMapper;
import edu.nciae.system.mapper.SysUserRoleMapper;
import edu.nciae.system.service.SysUserService;
import edu.nciae.system.domain.SysUser;
import edu.nciae.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 通过用户ID查询用户
     *
     * @param userId
     * @return
     */
    @Override
    public SysUser selectUserById(Integer userId) {
        return userMapper.selectUserById(userId);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName
     * @return
     */
    @Override
    public SysUser selectUserByLoginName(String userName) {
        return userMapper.selectUserByLoginName(userName);
    }

    @Override
    public int updateUser(SysUser sysUser) {
        return userMapper.updateByPrimaryKey(sysUser);
    }

    /**
     * 记录登陆信息
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(SysUser user, SysUser sysUser) {
        sysUser.setUpdateBy(user.getLoginName());
        sysUser.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateByPrimaryKey(sysUser);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public int updateUserInfo(SysUser user, UserVo uservo) {
        user.setPassword(uservo.getPassword());
        user.setEmail(uservo.getEmail());
        user.setUserName(uservo.getUsername());
        user.setUpdateBy(user.getLoginName());
        user.setUpdateTime(DateUtils.getNowDate());
        return userMapper.updateUser(user);
    }

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = userMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkPhoneUnique(user.getPhonenumber());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_PHONE_NOT_UNIQUE;
        }
        return UserConstants.USER_PHONE_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        SysUser info = userMapper.checkEmailUnique(user.getEmail());
        if (StringUtils.isNotNull(info) && info.getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SysUser user) {
        // 新增用户信息
        int rows = userMapper.insertUser(user);
        // 新增用户与角色管理
        insertUserRole(user);
        return rows;
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(SysUser user) {
        List<Integer> roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles)) {
            // 新增用户与角色管理
            List<SysUserRole> list = new ArrayList<SysUserRole>();
            for (Integer roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 用户状态修改
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysUser user) {
        if (user.getUserId() == 1) {
            throw new BusinessException("不允许修改超级管理员用户");
        }
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteUserByIds(String ids) throws Exception {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            if (userId == 1) {
                throw new BusinessException("不允许删除超级管理员用户");
            }
        }
        return userMapper.deleteUserByIds(userIds);
    }

    /**
     * 修改用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int resetUserPwd(SysUser user) {
        return updateUserInfo(user);
    }

    /**
     * 修改用户个人详细信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public int updateUserInfo(SysUser user) {
        return userMapper.updateUser(user);
    }

}
