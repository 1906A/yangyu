package com.leyou.service;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecParamService {
    @Autowired
    SpecParamMapper specParamMapper;

    /**
     * 根据组id查询参数列表
     * @param gid
     * @return
     */
    public List<SpecParam> findSpecParam(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        return specParamMapper.select(specParam);
    }

    /**
     *新增规格参数组下的参数
     * @param specParam
     */
    public void saveSpecParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    /**
     *修改规格参数组下的参数
     * @param specParam
     */
    public void updateSpecParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据id删除规格参数组下的参数
     * @param id
     */
    public void deleteParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据分类id查询规格参数
     * @param cid
     * @return
     */
    public List<SpecParam> findParamsByCid(Long cid) {
        return specParamMapper.findParamsByCid(cid);
    }
    /**
     * 根据三级+可搜索条件为1的参数查询规格参数
     * @param cid
     * @return
     */
    public List<SpecParam> findParamsByCidandSearching(Long cid) {
        SpecParam specParam = new SpecParam();
        specParam.setCid(cid);
        specParam.setSearching(true);
        return specParamMapper.select(specParam);
    }
}
