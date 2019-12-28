package edu.nciae.system.service.impl;

import edu.nciae.common.core.text.Convert;
import edu.nciae.common.exception.BusinessException;
import edu.nciae.system.domain.SysRole;
import edu.nciae.system.mapper.SysRoleMapper;
import edu.nciae.system.mapper.SysUserRoleMapper;
import edu.nciae.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper roleMapper;
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return selectRoleList(new SysRole());

    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 角色状态修改
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int changeStatus(SysRole role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 批量删除角色信息
     *
     * @param ids 需要删除的数据ID
     * @throws Exception
     */
    @Override
    public int deleteRoleByIds(String ids) throws BusinessException {
        int result = 0;
        Integer[] roleIds = Convert.toIntArray(ids);
        for (Integer roleId : roleIds) {
            SysRole role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        if (roleIds.length > 0) {
            result = roleMapper.deleteRoleByIds(roleIds);
        }
        return result;
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Integer roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public int countUserRoleByRoleId(Integer roleId) {
        return userRoleMapper.countUserRoleByRoleId(roleId);
    }
}
