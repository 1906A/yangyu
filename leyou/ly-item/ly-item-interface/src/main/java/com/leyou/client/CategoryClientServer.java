package com.leyou.client;

import com.leyou.pojo.Category;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("category")
public interface CategoryClientServer {
    @RequestMapping("getCategoryById")
    public Category getCategoryById(@RequestParam("id") Long id);
}