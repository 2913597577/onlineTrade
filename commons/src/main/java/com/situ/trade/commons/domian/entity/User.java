package com.situ.trade.commons.domian.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
//UserDetails 里面就实现了Serializable接口，所以user也相当于实现了Serializable接口
public class User implements UserDetails {
   private Integer userId;
   private String username;
   private String password;
   private String paypassword;
   private String realname;
   private String email;
   private String phone;
   private String address;
   private Integer status;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GTM+8")
   private Date createTime;
   private String image;
   private String idcard;
   private BigDecimal balance;
   @JsonIgnore
   private String salt;
   private Integer role;
   private Integer storeId;
   private Boolean permission;

   private Stores store;

   @Override
   @JsonIgnore
   public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<GrantedAuthority> authorities = new HashSet<>();
      authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
      return authorities;
   }

   @Override
   @JsonIgnore
   public boolean isAccountNonExpired() {
      return UserDetails.super.isAccountNonExpired();
   }

   @Override
   @JsonIgnore
   public boolean isCredentialsNonExpired() {
      return UserDetails.super.isCredentialsNonExpired();
   }

   @Override
   @JsonIgnore
   public boolean isAccountNonLocked() {
      return UserDetails.super.isAccountNonLocked();
   }

   @Override
   @JsonIgnore
   public boolean isEnabled() {
      return UserDetails.super.isEnabled();
   }
}
