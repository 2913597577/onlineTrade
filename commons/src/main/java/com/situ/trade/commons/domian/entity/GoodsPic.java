package com.situ.trade.commons.domian.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class GoodsPic implements Serializable {
    private Integer id;
    private Integer goodsId;
    private String url;
}
