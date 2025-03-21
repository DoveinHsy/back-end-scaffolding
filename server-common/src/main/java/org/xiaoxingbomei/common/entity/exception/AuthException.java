package org.xiaoxingbomei.common.entity.exception;

import org.xiaoxingbomei.common.entity.ErrorCode;

/**
 * 权限异常
 */
public class AuthException extends RuntimeException
{

    private static final long seriaVersionUID = 1L;

    private ErrorCode errorCode;

    public AuthException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AuthException(String message, ErrorCode errorCode)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public AuthException(String message, Throwable cause, ErrorCode errorCode)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public AuthException(Throwable cause, ErrorCode errorCode)
    {
        super(cause);
        this.errorCode = errorCode;
    }

    public AuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ErrorCode errorCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
