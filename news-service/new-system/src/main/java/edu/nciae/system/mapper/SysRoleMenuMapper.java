package edu.nciae.system.mapper;

import edu.nciae.system.domain.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(@Param("roleId") Integer roleId, @Param("menuId") Integer menuId);

    int insert(SysRoleMenu record);

    List<SysRoleMenu> selectAll();
}