package com.inspur.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:10
 */
@Data
@Table(name="tb_spu_detail")
public class SpuDetail implements Serializable {

    private static final long serialVersionUID = -168734457701598350L;

    @Id
    private Long spuId;// 对应的SPU的id
    private String description;// 商品描述
    private String specialSpec;// 商品特殊规格的名称及可选值模板
    private String genericSpec;// 商品的全局规格属性
    private String packingList;// 包装清单
    private String afterService;// 售后服务
    // 省略getter和setter


    @Override
    public String toString() {
        return "SpuDetail{" +
                "spuId=" + spuId +
                ", description='" + description + '\'' +
                ", specialSpec='" + specialSpec + '\'' +
                ", genericSpec='" + genericSpec + '\'' +
                ", packingList='" + packingList + '\'' +
                ", afterService='" + afterService + '\'' +
                '}';
    }
}