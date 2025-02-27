package org.xiaoxingbomei.exception;

import lombok.Getter;
import lombok.Setter;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;

/**
 * 业务异常
 */
@Getter
@Setter
public class BusinessException extends RuntimeException
{
    //
    private static final long seriaVersionUID = 1L;

    //
    private String errorCode;
    private String errorMessage;

    //
    public BusinessException(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    //
    public BusinessException(String errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }






}