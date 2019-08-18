package com.inspur.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-12 16:09
 */
@Data
@Table(name = "tb_spec_param")
public class SpecParam implements Serializable {
    private static final long serialVersionUID = -795683228494662515L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long cid;
    private Long groupId;
    private String name;
    @Column(name = "`numeric`")
    private Boolean numeric;
    private String unit;
    private Boolean generic;
    private Boolean searching;
    private String segments;

    // getter和setter ...

    @Override
    public String toString() {
        return "SpecParam{" +
                "id=" + id +
                ", cid=" + cid +
                ", groupId=" + groupId +
                ", name='" + name + '\'' +
                ", numeric=" + numeric +
                ", unit='" + unit + '\'' +
                ", generic=" + generic +
                ", searching=" + searching +
                ", segments='" + segments + '\'' +
                '}';
    }
}
