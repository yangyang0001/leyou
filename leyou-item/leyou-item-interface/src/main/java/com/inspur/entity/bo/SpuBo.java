package com.inspur.entity.bo;

import com.inspur.entity.Sku;
import com.inspur.entity.Spu;
import com.inspur.entity.SpuDetail;
import lombok.Data;

import javax.naming.directory.SearchResult;
import java.io.Serializable;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 22:33
 */
@Data
public class SpuBo extends Spu implements Serializable {
    private static final long serialVersionUID = -9178364484262424971L;

    private String cname;
    private String bname;

    private List<Sku> skus;

    private SpuDetail spuDetail;

    @Override
    public String toString() {
        return "SpuBo{" +
                "cname='" + cname + '\'' +
                ", bname='" + bname + '\'' +
                ", skus=" + skus +
                ", spuDetail=" + spuDetail +
                '}';
    }
}
