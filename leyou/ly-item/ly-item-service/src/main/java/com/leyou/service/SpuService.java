package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询商品列表
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    public PageResult<SpuVo> page(String key, Integer saleable, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);
        List<SpuVo> spuList = spuMapper.page(key, saleable);
        PageInfo<SpuVo> spuPageInfo = new PageInfo<SpuVo>(spuList);
        return new PageResult<SpuVo>(spuPageInfo.getTotal(),spuPageInfo.getList(),20);
    }

    /**
     * 保存商品信息
     * @param spuVo
     */
    public void saveSpuDatail(SpuVo spuVo) {
        Date date = new Date();
        /**
         * 1、保存spu主表
         * 2、保存spu_detail
         * 3、保存sku
         * 4、保存stock表
         */
        Spu spu = new Spu();
        spu.setTitle(spuVo.getTitle());
        spu.setSubTitle(spuVo.getSubTitle());
        spu.setBrandId(spuVo.getBrandId());
        spu.setCid1(spuVo.getCid1());
        spu.setCid2(spuVo.getCid2());
        spu.setCid3(spuVo.getCid3());
        spu.setSaleable(false);//默认保存时不上架
        spu.setValid(true);
        spu.setCreateTime(date);
        spu.setLastUpdateTime(date);

        spuMapper.insert(spu);

        //第一种拿数据的办法
        SpuDetail spuDetail = new SpuDetail();

        spuDetail.setSpuId(spu.getId());
        spuDetail.setAfterService(spuVo.getSpuDetail().getAfterService());
        spuDetail.setDescription(spuVo.getSpuDetail().getDescription());
        spuDetail.setGenericSpec(spuVo.getSpuDetail().getGenericSpec());
        spuDetail.setPackingList(spuVo.getSpuDetail().getPackingList());
        spuDetail.setSpecialSpec(spuVo.getSpuDetail().getSpecialSpec());
        /*//第二种
        SpuDetail spuDetail = spuVo.getSpuDetail();
        spuDetail.setSpuId(spuVo.getId());*/

        spuDetailMapper.insert(spuDetail);
        List<Sku> skus = spuVo.getSkus();

        skus.forEach(sku ->{
            sku.setSpuId(spu.getId());
            sku.setEnable(true);
            sku.setCreateTime(date);
            sku.setLastUpdateTime(date);
            skuMapper.insert(sku);
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockMapper.insert(stock);
        });
    }

    /**
     * 查询商品详情
     * @param id
     * @return
     */
    public SpuDetail findDetail(Long id) {
        return spuDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询商品集合
     * @param id
     * @return
     */
    public List<Sku> findById(Long id) {
        List<Sku> skuList = skuMapper.selectByPrimaryKeys(id);
        return skuList;
    }

    /**
     * 查询未查询的数据
     * @param id
     * @return
     */
    public Spu baseInfo(Long id) {
        return spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 修改商品
     * @param spuVo
     */
    public void updateSpuDatail(SpuVo spuVo) {

        /**
         * 1、修改spu主表
         * 2、修改spu_detail
         * 3、删除sku
         * 4、删除库存
         * 5、修改sku
         * 6、修改stock表
         */
        Date date = new Date();
        //修改spu
        spuVo.setSaleable(null);
        spuVo.setValid(null);
        spuVo.setCreateTime(null);
        spuVo.setLastUpdateTime(date);
        spuMapper.updateByPrimaryKeySelective(spuVo);

        //修改spu——detail
        SpuDetail spuDetail = new SpuDetail();
        spuDetail.setSpuId(spuVo.getId());
        spuDetailMapper.updateByPrimaryKeySelective(spuDetail);

        //删除sku
        List<Sku> skuList = skuMapper.selectByPrimaryKeys(spuVo.getId());

        skuList.forEach(sku ->{
            skuMapper.deleteByPrimaryKey(sku.getId());
            //删除库存
            stockMapper.deleteByPrimaryKey(sku.getId());
        });
        List<Sku> skus = spuVo.getSkus();
        skus.forEach(sku1 ->{
            //添加sku
            sku1.setSpuId(spuVo.getId());
            sku1.setCreateTime(date);
            sku1.setLastUpdateTime(date);
            skuMapper.insert(sku1);
            //添加stock库存表
            Stock stock = new Stock();
            stock.setSkuId(sku1.getId());
            stock.setStock(sku1.getStock());
            stockMapper.insert(stock);
        });
    }

    public void editSpuStatus(Boolean saleable,Long id) {
        spuMapper.updateBySaleable(saleable,id);
    }

    public void deleteSpuStatus(Boolean valid, Long id) {
        spuMapper.updateByValid(valid,id);
    }
}
