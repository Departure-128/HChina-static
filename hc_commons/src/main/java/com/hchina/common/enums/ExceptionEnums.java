package com.hchina.common.enums;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnums {
    //1XXXX：用户问题
    //2XXXX：货源问题
    //3XXXX：车源问题

    INVALID_USERNAME_PASSWORD(1001,"用户名或密码错误"),
    GOODS_SPU_IS_EMPTY(2000,"该货源不存在！！"),
    INVALID_VERFIY_CODE(2006,"无效的验证码"),
    PRICE_CANNOT_BE_NULL(400,"价格不能为空！！"),
    CATEGORY_IS_EMPTY(3000,"该分类不存在！！"),
    FAIL_DELETE_USER(3001,"删除失败！！");



    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}