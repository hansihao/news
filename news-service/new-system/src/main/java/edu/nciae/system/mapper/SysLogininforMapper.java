package edu.nciae.system.mapper;

import edu.nciae.system.domain.SysLogininfor;

import java.util.List;

public interface SysLogininforMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(SysLogininfor record);

    SysLogininfor selectByPrimaryKey(Integer infoId);

    List<SysLogininfor> selectAll();

    int updateByPrimaryKey(SysLogininfor record);

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(SysLogininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    List<SysLogininfor> selectLogininforList(SysLogininfor logininfor);

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteLogininforByIds(String[] ids);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLogininfor();
}