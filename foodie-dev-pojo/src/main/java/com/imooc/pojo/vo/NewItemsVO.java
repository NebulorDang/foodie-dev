package com.imooc.pojo.vo;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * 最新商品VO
 */
@ApiModel(value = "最新商品VO", description = "最新商品VO")
public class NewItemsVO {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemsVO> simpleItemList;

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemsVO> getSimpleItemList() {
        return simpleItemList;
    }

    public void setSimpleItemList(List<SimpleItemsVO> simpleItemList) {
        this.simpleItemList = simpleItemList;
    }
}
