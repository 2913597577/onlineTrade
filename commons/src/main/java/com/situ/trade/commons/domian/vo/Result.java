package com.situ.trade.commons.domian.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;

    public static Result success(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }
    public static Result success(Integer code, Object data) {
        return success(code, "操作成功", data);
    }
    public static Result success(Object data){
        return success(200, data);
    }
    public static Result success(String msg, Object data){
        return success(200, msg, data);
    }
    public static Result success(){
        return success(null);
    }

    public static Result error(Integer code, String msg, Object data){
        return new Result(code, msg, data);
    }
    public static Result error(Integer code, String msg){
        return error(code, msg, null);
    }
    public static Result error(String msg){
        return error(0, msg);
    }

    /**
     *将当前对象，转换成JSON的字符串
     * @return
     */
    public String toJson() throws JsonProcessingException {
        //当前对象就是this
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
