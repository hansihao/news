package edu.nciae.system.mapper;

import edu.nciae.system.domain.SysDept;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(SysDept record);

    SysDept selectByPrimaryKey(Integer deptId);

    List<SysDept> selectAll();

    int updateByPrimaryKey(SysDept record);

    /**
     * 查询部门管理数据
     * @param dept 部门信息
     * @return 部门信息集合
     */
    List<SysDept> selectDeptList(SysDept dept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDept selectDeptById(Integer deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    SysDept checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Integer parentId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int insertDept(SysDept dept);

    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    int updateDept(SysDept dept);

    /**
     * 修改所在部门的父级部门状态
     *
     * @param dept 部门
     */
    void updateDeptStatus(SysDept dept);

    /**
     * 根据ID查询所有子部门
     * @param id
     * @return
     */
    List<SysDept> selectChildrenDeptById(Integer id);

    /**
     * 根据角色编号查询所有部门ID
     * @param roleId
     * @return
     * @author zmr
     */
    Set<String> selectRoleDeptIds(Integer roleId);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    int deleteDeptById(Integer deptId);
}