package com.imooc.controller;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("items")
@Api(value = "商品详情页展示接口", tags = {"商品详情页展示接口"})
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId
    ) {
        if(StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("商品id不能为空");
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return IMOOCJSONResult.ok(itemInfoVO);
    }

}

