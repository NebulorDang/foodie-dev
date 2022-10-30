package com.imooc.service;

import com.imooc.pojo.*;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param isShow
     * @return
     */
    public Items queryItemById(String itemId);
    /**
     * 根据商品id查询商品图片列表
     * @param Id
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     * @param Id
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param Id
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

}



