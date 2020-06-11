package com.leyou.dao;

import com.leyou.pojo.Spu;
import com.leyou.vo.SpuVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SpuMapper extends Mapper<Spu> {
    List<SpuVo> page(@Param("key") String key,
                     @Param("saleable") Integer saleable);


    @Update("update tb_spu set saleable =#{saleable} where id = #{id}")
    void updateBySaleable(Boolean saleable,Long id);
    @Update("update tb_spu set valid =#{valid} where id = #{id}")
    void updateByValid(Boolean valid,Long id);
}
