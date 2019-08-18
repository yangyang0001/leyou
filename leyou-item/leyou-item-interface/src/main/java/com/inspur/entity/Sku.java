package com.inspur.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-13 12:57
 */
@Data
@Table(name = "tb_sku")
public class Sku implements Serializable {
    private static final long serialVersionUID = -3162768282171234864L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long spuId;
    private String title;
    private String images;
    private Long price;
    private String ownSpec;// 商品特殊规格的键值对
    private String indexes;// 商品特殊规格的下标
    private Boolean enable;// 是否有效，逻辑删除用
    private Date createTime;// 创建时间
    private Date lastUpdateTime;// 最后修改时间
    @Transient
    private Integer stock;// 库存

    @Override
    public String toString() {
        return "Sku{" +
                "id=" + id +
                ", spuId=" + spuId +
                ", title='" + title + '\'' +
                ", images='" + images + '\'' +
                ", price=" + price +
                ", ownSpec='" + ownSpec + '\'' +
                ", indexes='" + indexes + '\'' +
                ", enable=" + enable +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", stock=" + stock +
                '}';
    }
}
