package edu.nciae.system.mapper;

import edu.nciae.system.domain.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);

    /**
     * 根据条件分页查询角色数据
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    List<SysRole> selectRoleList(SysRole role);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    int updateRole(SysRole role);

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    SysRole selectRoleById(Integer roleId);

    /**
     * 批量角色用户信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteRoleByIds(Integer[] ids);
}