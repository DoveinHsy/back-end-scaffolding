package org.xiaoxingbomei.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomException extends RuntimeException
{
    private static final long seriaVersionUID = 1L;

    // 错误代码
    private String errorCode;

    // 错误描述
    private String errorMessage;

    public CustomException(String errorMessage)
    {
        super(errorMessage);
    }

    public CustomException(String errorCode, String errorMessage){
        super(errorCode);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
