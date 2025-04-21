package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class Carts implements Serializable {
    private Integer cartId;
    private Integer userId;
    private Integer goodsId;
    private Integer quantity;
    private BigDecimal price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createTime;

    //商品信息
    private Goods goods;

}
