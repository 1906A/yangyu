package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuMapper extends Mapper<Sku> {
    @Insert("insert into tb_sku values(#{id},#{spuId},#{title},#{images},#{price},#{ownSpec},#{indexes},#{enable},#{createTime},#{lastUpdateTime})")
    void inserts(Sku sku);

    @Select("select ts.*,tsk.stock from tb_sku ts,tb_stock tsk where ts.id = tsk.sku_id and ts.spu_id = #{id}")
    List<Sku> selectByPrimaryKeys(Long id);
}
