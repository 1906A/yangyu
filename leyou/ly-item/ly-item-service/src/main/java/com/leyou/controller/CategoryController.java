package com.leyou.controller;

import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("list")
    public List<Category> list(@RequestParam("pid") Long pid){
        Category category = new Category();
        category.setParentId(pid);
        return categoryService.findByOne(category);
    }
    @RequestMapping("pid")
    public Category pid(){
        return categoryService.findone(6);
    }

    @RequestMapping("add")
    public String add(@RequestBody Category category){
        String result = "SUCC";
        try {
            categoryService.cateGoryadd(category);
        }catch (Exception e){
            System.out.println("添加商品分类异常");
            result = "FAIL";
        }
        return result;
    }

    @RequestMapping("update")
    public String update(@RequestBody Category category){
        String result = "SUCC";
        try {
            categoryService.cateGoryUpdate(category);
        }catch (Exception e){
            System.out.println("修改商品分类异常");
            result = "FAIL";
        }
        return result;
    }
    @RequestMapping("deleteById")
    public String deleteById(@RequestParam("id") Long id){
        String result = "SUCC";
        try {
            categoryService.deleteById(id);
        }catch (Exception e){
            System.out.println("删除商品分类异常");
            result = "FAIL";
        }
        return result;
    }

    @RequestMapping("getCategoryById")
    public Object getCategoryById(@RequestParam("id") Long id){
        return categoryService.getCategoryById(id);
    }


















}
