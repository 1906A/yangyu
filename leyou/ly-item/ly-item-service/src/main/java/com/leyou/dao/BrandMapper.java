package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BrandMapper extends tk.mybatis.mapper.common.Mapper<Brand> {

    List<Brand> findPage(@Param("key") String key,
                         @Param("sortBy") String sortBy,
                         @Param("desc") boolean desc);

    List<Brand> findByPage(@Param("key") String key,
                           @Param("i") int i,
                           @Param("rows") Integer rows,
                           @Param("sortBy") String sortBy,
                           @Param("desc") boolean desc);

    Long findCount(@Param("key") String key,
                   @Param("sortBy") String sortBy,
                   @Param("desc") boolean desc);

    @Insert("insert into tb_category_brand values(#{bid},#{cid})")
    void addBrandCategory(Long bid, Long cid);

    @Delete("delete from tb_category_brand where category_id = #{id}")
    void deleteBrandAndCategory(Long id);

    @Select("select * from tb_category where id in (select c1.category_id from tb_brand b,tb_category_brand c1 where #{bid}=c1.brand_id)")
    List<Category> findBid(Long bid);

    @Update("update tb_brand set name=#{name},image=#{image},letter=#{letter} where id=#{id}")
    void update(Brand brand);

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    void deleteBrandbyCategory(Long id);

    @Select("select d.* from tb_brand d,tb_category_brand b where d.id=b.brand_id and b.category_id = #{cid}")
    List<Brand> findByBrand(Long cid);
}
