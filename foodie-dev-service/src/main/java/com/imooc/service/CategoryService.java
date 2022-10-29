package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询所有一级分类
     * @param isShow
     * @return
     */

    public List<Category> queryAllRootLeveCat();

    /**
     * 根据一级分类id查询子分类信息
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);
}



