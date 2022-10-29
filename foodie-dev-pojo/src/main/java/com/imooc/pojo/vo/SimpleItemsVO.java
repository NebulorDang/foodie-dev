package com.imooc.pojo.vo;

import io.swagger.annotations.ApiModel;

/**
 * 6个最新商品的简单数据类型VO
 */
@ApiModel(value = "最新商品的简单数据类型VO", description = "最新商品的简单数据类型VO")
public class SimpleItemsVO {
    private String itemId;
    private String itemName;
    private String itemUrl;
    private String createdTime;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
