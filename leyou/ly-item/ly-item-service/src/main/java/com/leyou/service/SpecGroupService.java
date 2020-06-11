package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.pojo.Specgroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    public void saveSpecGroup(Specgroup specgroup) {
        specGroupMapper.insert(specgroup);
    }

    /**
     * 根据分类id查询商品规格组列表
     * @param cid
     * @return
     */
    public List<Specgroup> findSpecGroup(Long cid) {
        Specgroup specgroup = new Specgroup();
        specgroup.setCid(cid);
        return specGroupMapper.select(specgroup);
    }

    /**
     * 根据商品规格组id删除
     * @param id
     */
    public void deleteBySpecGroupId(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 修改商品规格组
     * @param specgroup
     */
    public void updateSpevGroup(Specgroup specgroup) {
        specGroupMapper.updateByPrimaryKey(specgroup);
    }
}
