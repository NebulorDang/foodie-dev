package com.imooc.service;

import com.imooc.pojo.OrderStatus;
import com.imooc.pojo.bo.ShopCartBO;
import com.imooc.pojo.bo.SubmitOrderBO;
import com.imooc.pojo.vo.OrderVO;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单相关信息
     * @param shopCartList
     * @param submitOrderBO
     */
    public OrderVO createOrder(List<ShopCartBO> shopCartList, SubmitOrderBO submitOrderBO);


    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单信息
     * @param orderId
     */
    public OrderStatus queryOrderStatusInfo(String orderId);


    /**
     * 关闭超时未支付订单
     */
    public void closeOrder();
}



