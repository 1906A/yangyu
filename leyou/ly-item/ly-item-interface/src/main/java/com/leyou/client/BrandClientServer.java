package com.leyou.client;

import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("brand")
public interface BrandClientServer {
    @RequestMapping("getBrandById")
    public Brand getBrandById(@RequestParam("brandId") Long brandId);
}