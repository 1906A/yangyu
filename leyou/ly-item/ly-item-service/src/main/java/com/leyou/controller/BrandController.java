package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    BrandService brandService;

    /**
     * 品牌管理中的分页查询
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @RequestMapping("page")
    public Object fingPage(@RequestParam("key") String key,
                           @RequestParam("page") Integer page,
                           @RequestParam("rows") Integer rows,
                           @RequestParam("sortBy") String sortBy,
                           @RequestParam("desc") boolean desc
                           ){
        System.out.println(key+"--"+page+"--"+rows+"--"+sortBy+"--"+desc);
        //PageResult<Brand> brandList = brandService.findPage(key,page,rows,sortBy,desc);
        PageResult<Brand> brandList = brandService.findByPage(key,page,rows,sortBy,desc);
        return brandList;
    }

    @RequestMapping("addOrEditBrand")
    public void addOrEditBrand(Brand brand,
                               @RequestParam("cids") List<String> cids){
        //brandService.addOrEditBrand(name,image,letter,cids);
        if(brand.getId()==null){
            brandService.addOrEditBrand(brand,cids);
        }else{
            brandService.update(brand,cids);
        }
    }

    @RequestMapping("deleteById/{id}")
    public void deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);
    }

    @RequestMapping("bid/{bid}")
    public List<Category> findBid(@PathVariable("bid") Long bid){
        return brandService.findBid(bid);
    }

    /**
     * 根据商品分类id查询品牌
     */
    @RequestMapping("cid/{cid}")
    public List<Brand> brand(@PathVariable("cid") Long cid){
        return brandService.findByBrand(cid);
    }

    @RequestMapping("getBrandById")
    public Brand getBrandById(@RequestParam("brandId") Long brandId){
        return brandService.getBrandById(brandId);
    }


















}
