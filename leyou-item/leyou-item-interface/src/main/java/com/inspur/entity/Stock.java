package com.inspur.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-13 13:01
 */
@Data
@Table(name = "tb_stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 1113997049943515770L;

    @Id
    private Long skuId;
    private Integer seckillStock;// 秒杀可用库存
    private Integer seckillTotal;// 已秒杀数量
    private Integer stock;// 正常库存

    @Override
    public String toString() {
        return "Stock{" +
                "skuId=" + skuId +
                ", seckillStock=" + seckillStock +
                ", seckillTotal=" + seckillTotal +
                ", stock=" + stock +
                '}';
    }
}