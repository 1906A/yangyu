package com.jiyun.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GoodsDetailController {
    @RequestMapping("/hello")
    public String hello(Model model){
        model.addAttribute("name","zhangsan");
        return "hello";
    }

}
