package com.leyou;

import com.leyou.client.SpuClient;
import com.leyou.common.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.GoodService;
import com.leyou.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchTest {

    @Autowired
    SpuClient spuClient;

    @Autowired
    GoodService goodService;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    GoodsRepository goodsRepository;
    @Test
    public void test1(){
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);

        PageResult<SpuVo> page = spuClient.page("", 2, 1, 200);
        page.getItems().forEach(spuVo -> {
            try {

                Goods goods = goodService.convert(spuVo);

                goodsRepository.save(goods);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void test2(){
        Optional<Goods> byId = goodsRepository.findById(9l);
        System.out.println();
    }
}
