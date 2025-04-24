package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class Order implements Serializable {
    private Integer orderId;
    private Integer status;
    private Integer amount;
    private Integer userId;
    private Integer goodsId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createTime;
    private String address;

    private Goods goods;
    private User user;

}
