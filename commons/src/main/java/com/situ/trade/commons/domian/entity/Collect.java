package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Collect {
    private Integer goodsId;
    private Integer userId;
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createTime;

    //商品信息
    private Goods goods;
}
