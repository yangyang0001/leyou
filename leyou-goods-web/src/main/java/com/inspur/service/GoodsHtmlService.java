package com.inspur.service;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-17 16:00
 */
@Service
public class GoodsHtmlService {


    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    private GoodsService goodsService;

    /**
     * 使用TemplateEngine 来初始化模板 引擎
     * @param spuId
     */
    public void createHtml(Long spuId) {
        //获取上下文对象
        Context context = new Context();
        Map<String, Object> resultMap = goodsService.loadData(spuId);
        //设置数据模型
        context.setVariables(resultMap);

        //这种静态的文件一般生成到 Nginx目录下
        File file = new File("D:\\nginx-1.14.1\\html\\item\\" + spuId + ".html");
        PrintWriter printWriter = null;
        try {
            printWriter = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.templateEngine.process("item", context, printWriter);

        if(printWriter != null) {
            printWriter.flush();
            printWriter.close();
        }
    }

    public void deleteHtml(Long spuId) {
        File file = new File("D:\\nginx-1.14.1\\html\\item\\" + spuId + ".html");
        file.deleteOnExit();
    }
}
