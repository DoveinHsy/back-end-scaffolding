package org.xiaoxingbomei.exception;

import lombok.Getter;
import lombok.Setter;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;

/**
 * 用户异常
 */
@Getter
@Setter
public class UserException extends RuntimeException
{
    //
    private static final long serialVersionUID = 1L;

    //
    private String errorCode;
    private String errorMessage;

    //
    public UserException(String message)
    {
        this.errorMessage = message;
    }

    //
    public UserException(String errorCode, String errorMessage)
    {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}