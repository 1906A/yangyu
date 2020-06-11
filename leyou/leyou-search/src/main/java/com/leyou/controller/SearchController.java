package com.leyou.controller;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.SpecClient;
import com.leyou.pojo.*;
import com.leyou.repository.GoodsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    SpecClient specClient;

    @RequestMapping("page")
    public Object findGoods(@RequestBody Search search1){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //queryBuilder.withQuery(QueryBuilders.matchQuery("all",search1.getKey()).operator(Operator.AND));//关键字
        BoolQueryBuilder all = QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("all", search1.getKey()).operator(Operator.AND));
        if(search1.getFilter()!=null&&search1.getFilter().size()>0){
            search1.getFilter().keySet().forEach(key->{
                String field = "specs."+key+".keyword";
                if(key.equals("分类")){
                    field = "cid3";
                }else if (key.equals("品牌")){
                    field = "brandId";
                }
                all.filter(QueryBuilders.termQuery(field,search1.getFilter().get(key)));
            });
        }
        queryBuilder.withQuery(all);
        queryBuilder.withPageable(PageRequest.of(search1.getPage()-1,search1.getSize()));//分页

        String brandAgg = "brandAgg";
        String categoryAgg = "categoryAgg";

        queryBuilder.addAggregation(AggregationBuilders.terms(brandAgg).field("brandId"));
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAgg).field("cid3"));

        AggregatedPage<Goods> search = (AggregatedPage<Goods>)goodsRepository.search(queryBuilder.build());

        List<Brand> brands = getBrandList(brandAgg,search);
        List<Category> categorys = getCrateList(categoryAgg,search);
        List<Map<String,Object>> list = null;
        if(categorys.size()==1){
            List<SpecParam> specParamList = specClient.findParamsByCidandSearching(categorys.get(0).getId());
            list = addAges(specParamList,queryBuilder);
        }

        SearchResult pageResult = new SearchResult(search.getTotalElements(),search.getContent(),search.getTotalPages(),brands,categorys,list);

        return pageResult;
    }

    private List<Map<String, Object>> addAges(List<SpecParam> specParamList, NativeSearchQueryBuilder queryBuilder) {
        //创建返回的结果集
        List<Map<String, Object>> list = new ArrayList<>();
        //根据可以查询的的属性，添加聚合
        specParamList.forEach(specParam ->{
            String key = specParam.getName();
            queryBuilder.addAggregation(AggregationBuilders.terms(key).field("specs."+key+".keyword"));
        });
        //执行查询
        AggregatedPage<Goods> search = (AggregatedPage<Goods>)goodsRepository.search(queryBuilder.build());
        //得到聚合结果
        Map<String, Aggregation> aggregationMap = search.getAggregations().asMap();
        String brandAgg = "brandAgg";
        String categoryAgg = "categoryAgg";
        //便利聚合结果
        aggregationMap.keySet().forEach(key->{
            //排除掉品牌和分来的聚合
            if(!(brandAgg.equals(key) || categoryAgg.equals(key))){
                //得到聚合对象
                StringTerms aggregation = (StringTerms)aggregationMap.get(key);
                Map<String,Object> map = new HashMap<>();
                List<Object> list1 = new ArrayList<>();
                map.put("k",key);
                //获取桶内的数据
                aggregation.getBuckets().forEach(bucket -> {
                    Map<String,Object> tmap = new HashMap<>();
                    tmap.put("name",bucket.getKeyAsString());
                    list1.add(tmap);
                });
                map.put("options",list1);
                list.add(map);
            }
        });
        return list;
    }

    @Autowired
    CategoryClient categoryClient;
    @Autowired
    BrandClient brandClient;


    public List<Brand> getBrandList(String brandAgg,AggregatedPage<Goods> search){
        List<Brand> brands = new ArrayList<>();
        LongTerms brandaggregation = (LongTerms)search.getAggregation(brandAgg);
        brandaggregation.getBuckets().forEach(bucket -> {
            Long brandId = (Long) bucket.getKey();
            brands.add(brandClient.getBrandById(brandId));
        });
        return brands;
    }


    public List<Category> getCrateList(String categoryAgg,AggregatedPage<Goods> search){
        List<Category> categorys = new ArrayList<>();
        LongTerms createaggregation = (LongTerms) search.getAggregation(categoryAgg);
        createaggregation.getBuckets().forEach(create->{
            Long cid3 = (Long) create.getKey();
            categorys.add(categoryClient.getCategoryById(cid3));
        });
        return categorys;
    }






















}
