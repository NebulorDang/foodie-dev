package com.imooc.service.center;

import com.imooc.pojo.Orders;
import com.imooc.pojo.vo.OrderStatusCountsVO;
import com.imooc.utils.PagedGridResult;

public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize);

    /**
     * 将订单状态改为商家发货
     * @param orderId
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态
     * @param orderId
     * @return
     */
    public boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单 （逻辑删除）
     * @param userId
     * @param orderId
     * @return
     */
    public boolean deleteOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     * @param userId
     * @return
     */
    public OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 获得分页的订单动向
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult getOrdersTrend(String userId,
                                         Integer page,
                                         Integer pageSize);
}
