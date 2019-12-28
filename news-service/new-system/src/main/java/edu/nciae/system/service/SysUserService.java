package edu.nciae.system.service;

import edu.nciae.system.domain.SysUser;
import edu.nciae.system.vo.UserVo;

import java.util.List;

/**
 * 用户 业务层
 */
public interface SysUserService {
    /**
     * 通过用户ID查询用户
     * @param userId
     * @return
     */
    SysUser selectUserById(Integer userId);

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    SysUser selectUserByLoginName(String userName);

    /**
     * 记录登陆信息
     * @return
     */
    int updateUser(SysUser sysUser);

    /**
     * 记录登陆信息
     * @param user
     * @return
     */
    int updateUser(SysUser user, SysUser sysUser);

    /**
     * 修改用户详细信息
     * @param user 用户信息
     * @return 结果
     */
    int updateUserInfo(SysUser user, UserVo uservo);

    /**
     * 根据条件分页查询用户列表
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUser> selectUserList(SysUser user);

    /**
     * 校验用户名称是否唯一
     * @param loginName 登录名称
     * @return 结果
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 校验手机号码是否唯一
     * @param user 用户信息
     * @return 结果
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     * @param user 用户信息
     * @return 结果
     */
    String checkEmailUnique(SysUser user);

    /**
     * 保存用户信息
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUser user);

    /**
     * 用户状态修改
     * @param user 用户信息
     * @return 结果
     */
    int changeStatus(SysUser user);

    /**
     * 批量删除用户信息
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteUserByIds(String ids) throws Exception;

    /**
     * 修改用户密码信息
     * @param user 用户信息
     * @return 结果
     */
    int resetUserPwd(SysUser user);

    /**
     * 修改用户个人详细信息
     * @param user
     * @return
     */
    int updateUserInfo(SysUser user);

}
