package com.inspur.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 16:08
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup implements Serializable {

    private static final long serialVersionUID = 4688363219959291882L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cid;

    private String name;

    //忽略 当前 列名称
    @Transient
    private List<SpecParam> params;

    // getter和setter省略


    @Override
    public String toString() {
        return "SpecGroup{" +
                "id=" + id +
                ", cid=" + cid +
                ", name='" + name + '\'' +
                ", params=" + params +
                '}';
    }
}