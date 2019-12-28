package edu.nciae.order.mapper;

import edu.nciae.order.domain.ShopOrderOperateHistory;

import java.util.List;

public interface ShopOrderOperateHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShopOrderOperateHistory record);

    ShopOrderOperateHistory selectByPrimaryKey(Long id);

    List<ShopOrderOperateHistory> selectAll();

    int updateByPrimaryKey(ShopOrderOperateHistory record);
}