package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    /**
     * 查询所有收货地址
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);

    /**
     * 新增收货地址
     * @param addressBO
     * @return
     */
    public void addUserAddress(AddressBO addressBO);

    /**
     * 修改收货地址
     * @param addressBO
     * @return
     */
    public void updateUserAddress(AddressBO addressBO);

    /**
     * 删除收货地址
     * @param userId
     * @param addressId
     * @return
     */
    public void deleteUserAddress(String userId, String addressId);

    /**
     * 设置默认收货地址
     * @param userId
     * @param addressId
     * @return
     */
    public void setDefaultUserAddress(String userId, String addressId);

    /**
     * 根据主键查询userAddress
     * @param addressId
     * @return userAddress
     */
    public UserAddress getUserAddressByPrimaryKey(String addressId);
}



