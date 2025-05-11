package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class Goods implements Serializable {
    private Integer goodsId;
    private String name;
    private BigDecimal price;
    private String description;
    private String score;
    private String detail;
    private String color;
    private String version;
    private BigDecimal markPrice;
    private BigDecimal purchasePrice;
    private Integer stock;
    private Integer sold;
    private Integer recom;
    private Integer typeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date updateTime;
    private Integer status;
    private Integer  storeId;
    private Integer afterSalePeriod;
    private String afterSaleService;

    private Type type;
    private List<GoodsPic> pics;
}
