package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    /**
     * 查询商品列表
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("page")
    public Object page(@RequestParam("key") String key,
                                  @RequestParam("saleable") Integer saleable,
                                  @RequestParam("page") Integer page,
                                  @RequestParam("rows") Integer rows
                          ){
        PageResult<SpuVo> spuList = spuService.page(key, saleable, page, rows);
        return spuList;
    }

    /**
     * 保存商品信息
     * @param spuVo
     */
    @RequestMapping("goods")
    public void saveSpuDatail(@RequestBody SpuVo spuVo){
        if(spuVo.getId()==null){
            spuService.saveSpuDatail(spuVo);
        }else{
            spuService.updateSpuDatail(spuVo);
        }
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    @RequestMapping("detail/{id}")
    public SpuDetail detail(@PathVariable("id") Long id){
        return spuService.findDetail(id);
    }

    /**
     * 查询未查询的数据
     * @param id
     * @return
     */
    @RequestMapping("baseInfo/{id}")
    public Spu baseInfo(@PathVariable("id") Long id){
        return spuService.baseInfo(id);
    }

    /**
     * 修改商品集下架上架，删除
     * @param id
     * @return
     */
    @RequestMapping("editSpuStatus")
    public void editSpuStatus(@RequestParam(value = "saleable",required = false) Boolean saleable,
                              @RequestParam(value = "id",required = false) Long id
                              ){
        spuService.editSpuStatus(saleable,id);
    }
    @RequestMapping("deleteSpuStatus")
    public void deleteSpuStatus(
                              @RequestParam(value = "valid",required = false) Boolean valid,
                              @RequestParam(value = "id",required = false) Long id
                              ){
        spuService.deleteSpuStatus(valid,id);
    }

}
