package com.inspur.goods.controller;

import com.inspur.service.GoodsHtmlService;
import com.inspur.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-16 17:15
 */
@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private GoodsHtmlService goodsHtmlService;

    /**
     * 点击 大图片 产生的链接 直接发送到这里,这里组织值来回显
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/item/{id}.html")
    public String toItemPage(@PathVariable("id") Long id, Model model) {
        System.out.println("goods-web ------------------------> toItemPage id:" + id);
        Map<String, Object> stringObjectMap = goodsService.loadData(id);
        model.addAllAttributes(stringObjectMap);
        //在Nginx下创建 Html文件
        goodsHtmlService.createHtml(id);
        return "item";
    }

}
