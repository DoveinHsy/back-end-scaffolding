package org.xiaoxingbomei.Enum;

/**
 * 通用返回枚举
 */
public enum GlobalCodeEnum
{


    // 通用码值-1
    SUCCESS("200","请求成功"),
    ERROR("999","请求失败"),


    // 权限-2
    AUTH_SUCCESS("2001","认证成功"),
    AUTH_ERROR("2002","认证失败"),


    // 用户-3
    USER_ACCESS_EXCEED("3001","访问次数超限"),

    // xxx-4



    // ESB-5
    ESB_CODE_SUCCESS("000000","操作成功"),
    ESB_ERROR_PROVIDER("5001","服务者错误"),
    ESB_ERROR_CONSUMER("5002","消费者错误"),
    ESB_ERROR_PARAM_MISSING("5003","参数缺失"),
    ESB_ERROR_PARAM_ERROR("5004","参数错误")


    // HTTP-6


    //

    ;



    String code;
    String message;

    GlobalCodeEnum(){}


    GlobalCodeEnum(String code,String message)
    {
        this.code    = code;
        this.message = message;
    }


    public String getCode()
    {
        return code;
    }
    public String getMessage()
    {
        return message;
    }


    public void setCode(String code)
    {
        this.code = code;
    }
    public void setMessage(String message)
    {
        this.message = message;
    }


    @Override
    public String toString()
    {
        return "GlobalCodeEnum{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }


}