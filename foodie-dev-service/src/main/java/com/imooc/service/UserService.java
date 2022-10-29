package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * @param userBO
     * @return Users
     */

    public Users createUser(UserBO userBO);

    /**
     * 检索用户名和密码
     * @param username
     * @param password
     * @return
     */
    public Users queryUserForLogin(String username, String password);
}
