package com.situ.trade.commons.domian.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class Type implements Serializable {
    private Integer typeId;
    private String typename;
    private Integer parentId;
    private String dscp;
    private String pic;
    private Integer status;
    private Integer recom;

    //父分类
    private Type parent;
    //分类下可以有很多的商品
    private List<Goods> goods;
    //子分类
    private List<Type> children;
}
