package com.situ.trade.commons.domian.vo;

import com.situ.trade.commons.domian.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo extends User {
    private String jwt;
    private String captcha;
    private String repassword;
}
