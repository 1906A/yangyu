package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.pojo.Specgroup;
import com.leyou.service.SpecGroupService;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecGroupService specGroupService;

    /*
    保存商品规格组
     */
    @RequestMapping("group")
    public void group(@RequestBody Specgroup specgroup){

        if(specgroup.getId()==null){
            specGroupService.saveSpecGroup(specgroup);
        }else{
            specGroupService.updateSpevGroup(specgroup);
        }
    }

    /**
     * 查询规格参数组列表
     * @return
     */
    @RequestMapping("groups/{cid}")
    public List<Specgroup> groups(@PathVariable("cid") Long cid){
        return specGroupService.findSpecGroup(cid);
    }

    /**
     * 根据商品规格组id删除
     * @param id
     */
    @RequestMapping("group/{cid}")
    public void deleteBySpecGroupId(@PathVariable("cid") Long id){
        specGroupService.deleteBySpecGroupId(id);
    }

    @Autowired
    private SpecParamService specParamService;
    /**
     * 根据组id查询参数列表
     * @param gid
     * @return
     */
    @RequestMapping("params")
    public List<SpecParam> findSpecParam(@RequestParam("gid") Long gid){
        return specParamService.findSpecParam(gid);
    }
}
