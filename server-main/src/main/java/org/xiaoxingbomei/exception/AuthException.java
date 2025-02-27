package org.xiaoxingbomei.exception;

import org.xiaoxingbomei.Enum.GlobalCodeEnum;

/**
 * 权限异常
 */
public class AuthException extends RuntimeException
{
    //
    private static final long seriaVersionUID = 1L;

    //
    private String errorCode;
    private String errorMessage;

    //
    public AuthException(String message)
    {
        super(message);
    }

    //
    public AuthException(String errorCode, String errorMessage)
    {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
