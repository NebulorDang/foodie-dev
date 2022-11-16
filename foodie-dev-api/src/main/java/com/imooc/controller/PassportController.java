package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.ShopCartBO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("passport")
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
public class PassportController extends BaseController {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "用户注册", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {

        // 1.判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }

        // 2.查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 3.请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 1.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 2.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已经存在");
        }

        // 3.密码长度不能少于6位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }

        // 4.判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return IMOOCJSONResult.errorMsg("两次密码输入不一致");
        }

        Users userResult = userService.createUser(userBO);
        setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return IMOOCJSONResult.ok(userResult);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("login")
    public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                 HttpServletRequest request,
                                 HttpServletResponse response) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 1.判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
        }

        // 2.实现登录
        Users userResult = userService.queryUserForLogin(username, password);

        if (userResult == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }
        setNullProperty(userResult);

//        response.setHeader("Set-Cookie", "JSESSIONID=xxx;SameSite=None;Secure");
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        // TODO 生成用户token，存入redis会话
        // 同步曾经的购物车数据
        syncShopCartData(userResult.getId(), request, response);

        return IMOOCJSONResult.ok(userResult);
    }

    /**
     * 注册登录成功后，同步cookie和redis中的购物车数据
     */
    private void syncShopCartData(String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        /**
         *  1. redis中无数据，如果cookie中的购物车为空，则不做任何处理
         *                  如果cookie中的购物车不为空，此时直接放入redis中
         *  2. redis中有数据，如果cookie中没有，则直接放入cookie中
         *                  如果cookie中不为空，则以cookie为准，cookie中的直接覆盖redis中的
         *  3. 同步redis之后，再覆盖本地cookie数据，保证cookie和redis同步
         */

        // 从redis中获取购物车
        String shopCartJsonRedis = redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        // 从cookie中获取购物车
        String shopCartJsonCookie = CookieUtils.getCookieValue(request, FOODIE_SHOPCART, true);
        if (StringUtils.isBlank(shopCartJsonRedis)) {
            if(StringUtils.isNotBlank(shopCartJsonCookie)) {
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, shopCartJsonCookie);
            }
        } else {
            if(StringUtils.isBlank(shopCartJsonCookie)) {
                CookieUtils.setCookie(request, response, FOODIE_SHOPCART, shopCartJsonRedis, true);
            }else {
                // 合并redis和cookie中的数据（公共部分以cookie为准）
                List<ShopCartBO> shopCartListRedis = JsonUtils.jsonToList(shopCartJsonRedis, ShopCartBO.class);
                List<ShopCartBO> shopCartListCookie = JsonUtils.jsonToList(shopCartJsonCookie, ShopCartBO.class);

                // 暂时存储cookie中独有的部分
                List<ShopCartBO> pendingAddList = new ArrayList<>();

                for(ShopCartBO cookieShopCart : shopCartListCookie) {
                    String cookieSpecId = cookieShopCart.getSpecId();
                    // 判断当前该规格商品是否redis和cookie中都有
                    boolean isCommon = false;
                    for(ShopCartBO redisShopCart : shopCartListRedis) {
                        String redisSpecId = redisShopCart.getSpecId();
                        if(cookieSpecId.equals(redisSpecId)) {
                            // 以cookie为准
                            redisShopCart.setBuyCounts(cookieShopCart.getBuyCounts());
                            isCommon = true;
                            break;
                        }
                    }
                    if(!isCommon) {
                        pendingAddList.add(cookieShopCart);
                    }
                }

                // 合并两个list
                shopCartListRedis.addAll(pendingAddList);

                // 更新到redis和cookie
                redisOperator.set(FOODIE_SHOPCART + ":" + userId, JsonUtils.objectToJson(shopCartListRedis));

                CookieUtils.setCookie(request, response, FOODIE_SHOPCART,
                        JsonUtils.objectToJson(shopCartListRedis), true);
            }
        }
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, "user");

        // 用户退出登录，需要清空购物车
        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

        // TODO 分布式会话中需要清楚用户数据
        return IMOOCJSONResult.ok();
    }


    private void setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
    }
}
