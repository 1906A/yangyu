package com.leyou.service;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    /**
     * 根据节点id查询所有分类信息
     * @param category
     * @return
     */
    public List<Category> findByOne(Category category){
        return categoryMapper.select(category);
    }

    public Category findone(int id){
        return categoryMapper.findByOne(id);
    }

    /**
     * 添加商品分类
     * @param category
     */
    public void cateGoryadd(Category category) {
        categoryMapper.insertSelective(category);
    }

    /**
     * 修改商品分类
     * @param category
     */
    public void cateGoryUpdate(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    /**
     * 根据节点id，进行删除
     * @param id
     */
    public void deleteById(Long id) {
        Category category = new Category();
        category.setId(id);
        categoryMapper.deleteByPrimaryKey(category);
    }

    public Category getCategoryById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }
}
