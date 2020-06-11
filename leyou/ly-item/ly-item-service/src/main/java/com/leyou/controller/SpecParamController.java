package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("specParam")
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService;

    /**
     *新增规格参数组下的参数
     * @param specParam
     */
    @RequestMapping("param")
    public void saveSpecParam(@RequestBody SpecParam specParam){
        if(specParam.getId()==null){
            specParamService.saveSpecParam(specParam);
        }else{
            specParamService.updateSpecParam(specParam);
        }
    }

    /**
     * 根据id删除规格参数组下的参数
     * @param id
     */
    @RequestMapping("param/{id}")
    public void deleteParam(@PathVariable("id") Long id){
        specParamService.deleteParam(id);
    }

    /**
     * 根据分类id查询规格参数
     * @param cid
     * @return
     */
    @RequestMapping("params")
    public List<SpecParam> findParamsByCid(@RequestParam("cid") Long cid){
        return specParamService.findParamsByCid(cid);
    }

    /**
     * 根据三级+可搜索条件为1的参数查询规格参数
     * @param cid
     * @return
     */
    @RequestMapping("paramsByCid")
    public List<SpecParam> findParamsByCidandSearching(@RequestParam("cid") Long cid){
        List<SpecParam> paramsByCidandSearching = specParamService.findParamsByCidandSearching(cid);
        return paramsByCidandSearching;
    }
}
