package com.inspur.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-09 16:47
 */
@Data
public class PageResult<T> implements Serializable {
    private static final long serialVersionUID = 1721675810626379805L;
    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageResult(){}

    public PageResult(Long total, List<T> items) {
        this.total = total;
        this.items = items;
    }

    public PageResult(Long total, Integer totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", totalPage=" + totalPage +
                ", items=" + items +
                '}';
    }
}
