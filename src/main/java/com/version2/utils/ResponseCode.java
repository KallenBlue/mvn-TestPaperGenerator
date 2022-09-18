package com.version2.utils;
/**
 * @Description: 响应码枚举，参考HTTP状态码的语义
 */
public enum ResponseCode {

    ON_LINE(0), //登录状态： 在线

    OFF_LINE(1), //登录状态：离线

    SUCCESS(200),  //成功

    FAIL(400),     //失败

    UNAUTHORIZED(401), //未认证（签名错误）

    NOT_FOUND(404),    //接口不存在

    INTERNAL_SERVER_ERROR(500), //服务器内部错误

    ParamLost(301),     //参数缺失

    IndexLost(300),    //指标不存在

    SqlConfigError(302), //sql配置错误

    hasNotAccess(303)    //没有指标查询权限
    ;


    public int value;

    ResponseCode(int value) {
        this.value = value;
    }
}
