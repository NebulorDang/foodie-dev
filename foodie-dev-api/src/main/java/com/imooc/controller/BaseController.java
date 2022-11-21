package com.imooc.controller;

import com.imooc.pojo.Orders;
import com.imooc.pojo.Users;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.center.MyOrdersService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.UUID;

@Controller
public class BaseController {
    public static final String FOODIE_SHOPCART = "shopcart";
    public static final String REDIS_USER_TOKEN = "redis_user_token";
    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    @Autowired
    private MyOrdersService myOrdersService;

    @Autowired
    private RedisOperator redisOperator;

    // 支付中心的调用地址
    String paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";        // produce

    // 微信支付成功 -> 支付中心 -> 天天吃货平台 -> 回调通知的url
//    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    //内网穿透
    String payReturnUrl = "http://mb376a.natappfree.cc/orders/notifyMerchantOrderPaid";

    // 用户上传头像的位置
//    public static final String IMAGE_USER_FACE_LOCATION = "/workspace/images/foodie/faces";

    public static final String IMAGE_USER_FACE_LOCATION =
            File.separator + "workspace" +
                    File.separator + "images" +
                    File.separator + "foodie" +
                    File.separator + "faces";

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     *
     * @return
     */
    public IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return IMOOCJSONResult.errorMsg("订单不存在！");
        }
        return IMOOCJSONResult.ok(order);
    }

    public UsersVO convertUserVO(Users user) {
        // 实现用户redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + user.getId(), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
