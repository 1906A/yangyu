package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandMapper brandMapper;

    public PageResult<Brand> findPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        PageHelper.startPage(page,rows);
        List<Brand> brands = brandMapper.findPage(key,sortBy,desc);
        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brands);
        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }
    /**
     * 手写sql做分页
     * (page-1)*rows,5
     * (当前页码-1)*条数
     */
    public PageResult<Brand> findByPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {
        List<Brand> brands = brandMapper.findByPage(key,(page-1)*rows,rows,sortBy,desc);
        //查询总条数
        Long count = brandMapper.findCount(key,sortBy,desc);
        return new PageResult<Brand>(count,brands);
    }

    public void addOrEditBrand(Brand brand,List<String> cids) {
        //1.保存brand
        brandMapper.insert(brand);
        //2.保存tb_category_brand
        cids.forEach(id->{
            brandMapper.addBrandCategory(Long.parseLong(id),brand.getId());
        });
    }

    public void deleteById(Long id) {
        Brand brand = new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);

        //删除关系表
        brandMapper.deleteBrandAndCategory(id);
    }

    public List<Category> findBid(Long bid) {

        return brandMapper.findBid(bid);
    }

    public void update(Brand brand, List<String> cids) {
        brandMapper.updateByPrimaryKey(brand);

        //删除所有的分类
        brandMapper.deleteBrandbyCategory(brand.getId());
        cids.forEach(id->{
            brandMapper.addBrandCategory(Long.parseLong(id),brand.getId());
        });
    }

    public List<Brand> findByBrand(Long cid) {
        return brandMapper.findByBrand(cid);
    }

    public Brand getBrandById(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }
}
