package com.leyou.client;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("specParam")
public interface SpecItemClientServer {
    @RequestMapping("paramsByCid")
    public List<SpecParam> findParamsByCidandSearching(@RequestParam("cid") Long cid);
}
