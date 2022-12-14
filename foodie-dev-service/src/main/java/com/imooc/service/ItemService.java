package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVO;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     *
     * @param isShow
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     *
     * @param Id
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     *
     * @param Id
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     *
     * @param Id
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品评价等级数量
     *
     * @param itemId
     * @return
     */
    public CommentLevelCountsVO queryCommentLevelCounts(String itemId);

    /**
     * 根据商品id和level查询商品的评价
     * @param itemId
     * @param level
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort,
                                              Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort,
                                       Integer page, Integer pageSize);
    /**
     * 根据规格ids查询最新的购物车中的商品数据（用于刷新渲染购物车中的商品数据）
     * @param sepcIds
     * @return
     */
    public List<ShopCartVO> searchItemsBySpecIds(String itemSpecIds);

    /**
     * 根据商品规格ID查询下详情
     * @param itemSpecId
     * @return
     */
    public ItemsSpec queryItemSpecById(String itemSpecId);

    /**
     * 根据商品id获得商品主图
     * @param itemId
     * @return
     */
    public String queryItemMainImgById(String itemId);

    /**
     * 扣减库存
     * @param sepcIdg
     * @param buyCounts
     */
    public void decreaseItemSpecStock(String specId, int buyCounts);
}



