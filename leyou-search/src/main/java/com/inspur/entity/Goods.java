package com.inspur.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-14 23:47
 */
@Data
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods implements Serializable {
    private static final long serialVersionUID = -4701600101618546310L;

    @Id
    private Long id;            // spuId
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;         // 所有需要被搜索的信息，包含标题，分类，甚至品牌
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;    // 卖点
    private Long brandId;       // 品牌id
    private Long cid1;          // 1级分类id
    private Long cid2;          // 2级分类id
    private Long cid3;          // 3级分类id
    private Date createTime;    // 创建时间
    private List<Long> price;   // 价格
    @Field(type = FieldType.Keyword, index = false)
    private String skus;                // List<sku>信息的json结构
    private Map<String, Object> specs;  // 可搜索的规格参数，key是参数名，值是参数值

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", all='" + all + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", brandId=" + brandId +
                ", cid1=" + cid1 +
                ", cid2=" + cid2 +
                ", cid3=" + cid3 +
                ", createTime=" + createTime +
                ", price=" + price +
                ", skus='" + skus + '\'' +
                ", specs=" + specs +
                '}';
    }
}
