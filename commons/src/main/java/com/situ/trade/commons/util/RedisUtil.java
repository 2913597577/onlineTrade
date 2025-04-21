package com.situ.trade.commons.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Set;

/**
 *这是一个用来访问Redis数据库的工具类
 *提供保存数据、查询数据、更新数据、删除数据
 * 缓存数据主要就是Java中的对象，采用Json的方式进行缓存
 */
@Component
public class RedisUtil {
    /*
     *redisTemplate.opsForValue();  //字符串
     * redisTemplate.opsForHash();
     * redisTemplate.opsForList();
     * redisTemplate.opsForSet();
     * redisTemplate.opsForZSet();
     */
    @Autowired //自动注入属性
    private RedisTemplate redisTemplate;

    /**
     * 写入数据
     */
    public boolean set(String key, Object value){
        //尝试写入
        try {
            ValueOperations<Serializable,Object> ops= redisTemplate.opsForValue();
            ops.set(key, value);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 读取数据
     */
    public Object get(String key){
        ValueOperations<Serializable,Object> ops= redisTemplate.opsForValue();
        return ops.get(key);
    }
    /**
     * 删除数据
     */
    public boolean delete(String key){
        return redisTemplate.delete(key);
    }
    /**
     * 删除符合条件的key
     */
    public boolean deletePattern(String pattern){
        //根据规则获取所有符合的key
        Set<Serializable> keys= redisTemplate.keys(pattern);
        if(keys.size()>0){
            redisTemplate.delete(keys);
            return true;
        }
         return false;
    }
}
