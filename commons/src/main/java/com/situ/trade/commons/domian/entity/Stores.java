package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class Stores implements Serializable {

    private Integer id;
    private String storeName;
    private Integer userId;
    private String address;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
    private Date createTime;
    private String score;
    private String ad;
}
