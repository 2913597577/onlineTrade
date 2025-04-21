package com.situ.trade.commons.domian.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Address implements Serializable {
    private Integer id;
    private String address;
    private Integer userId;
}
