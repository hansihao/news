package edu.nciae.system.service;

import edu.nciae.system.domain.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 查询所有角色
     * @return 角色列表
     */
    List<SysRole> selectRoleAll();

    /**
     * 根据条件分页查询角色数据
     * @param role
     * @return
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 角色状态修改
     *
     * @param role 角色信息
     * @return 结果
     */
    int changeStatus(SysRole role);

    /**
     * 批量删除角色用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     * @throws Exception 异常
     */
    int deleteRoleByIds(String ids) throws Exception;

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole selectRoleById(Integer roleId);

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    int countUserRoleByRoleId(Integer roleId);
}
