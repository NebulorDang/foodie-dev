package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBO;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private Sid sid;
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addUserAddress(AddressBO addressBO) {
        // 1. 判断当前用户是否存在地址，如果没有，则新增为默认地址
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBO.getUserId());
        if (addressList == null || addressList.isEmpty()) {
            isDefault = 1;
        }

        String addressId = sid.nextShort();
        // 2. 保存地址到数据库
        UserAddress userAddress = new UserAddress();
/*        userAddress.setUserId(addressBO.getUserId());
        userAddress.setReceiver(addressBO.getReceiver());
        userAddress.setMobile(addressBO.getMobile());
        userAddress.setProvince(addressBO.getProvince());
        userAddress.setCity(addressBO.getCity());
        userAddress.setDetail(addressBO.getDetail());*/
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBO addressBO) {
        String addressId = addressBO.getAddressId();

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);

        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", addressId);
        criteria.andEqualTo("userId", userId);

        userAddressMapper.deleteByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void setDefaultUserAddress(String userId, String addressId) {
        // 1.将默认地址设置为不默认
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        for (UserAddress ua : userAddressList) {
            if(ua.getIsDefault() == 1) {
                ua.setIsDefault(YesOrNo.NO.type);
                userAddressMapper.updateByPrimaryKeySelective(ua);
                break;
            }
        }

        // 2. 将传入的地址设置为默认地址
        UserAddress defaultUserAddress = new UserAddress();
        defaultUserAddress.setId(addressId);
        defaultUserAddress.setUserId(userId);
        defaultUserAddress.setIsDefault(YesOrNo.YSE.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultUserAddress);
    }
}
