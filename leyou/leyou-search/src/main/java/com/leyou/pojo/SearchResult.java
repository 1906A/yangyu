package com.leyou.pojo;

import com.leyou.common.PageResult;

import java.util.List;
import java.util.Map;

public class SearchResult extends PageResult<Goods> {
    private List<Brand> brandList;
    private List<Category> categoryList;
    private List<Map<String,Object>> specParam;

    public List<Brand> getBrandList() {
        return brandList;
    }

    public void setBrandList(List<Brand> brandList) {
        this.brandList = brandList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Map<String, Object>> getSpecParam() {
        return specParam;
    }

    public void setSpecParam(List<Map<String, Object>> specParam) {
        this.specParam = specParam;
    }

    public SearchResult(Long total, List<Goods> items, Integer totalPage, List<Brand> brandList, List<Category> categoryList, List<Map<String, Object>> specParam) {
        super(total, items, totalPage);
        this.brandList = brandList;
        this.categoryList = categoryList;
        this.specParam = specParam;
    }
}
