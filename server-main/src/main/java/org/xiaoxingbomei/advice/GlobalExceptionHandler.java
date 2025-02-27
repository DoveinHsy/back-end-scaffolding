package org.xiaoxingbomei.advice;

import cn.dev33.satoken.exception.*;
import cn.dev33.satoken.util.SaResult;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResourceAccessException;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.exception.BusinessException;
import org.xiaoxingbomei.exception.UserException;
import org.xiaoxingbomei.utils.Exception_Utils;

import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import org.xiaoxingbomei.common.entity.GlobalEntity;

/**
 * 全局异常处理
 */
@EqualsAndHashCode
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{



    /**
     * 0、Sa-token
     *  - NotLoginException
     *  - NotPermissionException
     *  - NotRoleException
     *  - NotSafeException
     *  - DisableServiceException
     *  - NotBasicAuthException
     * 1、服务调用
     *  - http超时
     *  - http缺少入参
     *  - http异常
     *  - feign超时
     *  - feign异常
     *  - redis超时
     *  - redis异常
     *  - kafka超时
     *  - kafka异常
     *  - rabbitmq超时
     *  - rabbitmq异常
     *  - es超时
     *  - es异常
     *  - mongodb超时
     *  - mongodb异常
     *  - hbase超时
     *  - hbase异常
     *  - eureka超时
     *  - eureka异常
     *  - oss超时
     *  - oss异常
     *  - minio超时
     *  - minio异常
     *  - swagger超时
     *  - swagger异常
     *  - caffeine超时
     *  - caffeine异常
     *  - mybatis超时
     *  - mybatis异常
     * 2、数据库
     *  - 语法错误
     *  - 数据库连接失败
     *  - 数据库异常
     *  - 数据库超时
     *  - mybatis错误
     *  - mysql错误
     *  - oracle错误
     *  - db2错误
     * 3、Java
     *  - 空指针
     *  - 类型转换
     *  - 数组越界
     *  - 数字格式
     *  - 日期格式
     *  - 请求入参异常
     *  - 文件上传
     *  - 文件下载
     *  - 网络异常
     *  - 线程异常
     *  - 死锁
     *  - 内存溢出
     *  - 栈溢出
     *  - 运行时异常
     *  - 编译时异常
     *  - Exception
     *  - Throwable
     *
     */


    /**
     * 0、sa-token
     */

    // 拦截：未登录异常
    @ExceptionHandler(NotLoginException.class)
    public GlobalEntity handlerException(NotLoginException e)
    {
        // 打印堆栈，以供调试
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        // 返回给前端
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(),"权限不足，请联系管理员xxx","");
    }


    // 拦截：缺少权限异常
    @ExceptionHandler(NotPermissionException.class)
    public SaResult handlerException(NotPermissionException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return SaResult.error("缺少权限：" + e.getMessage()); // e.getPermission()
    }


    // 拦截：缺少角色异常
    @ExceptionHandler(NotRoleException.class)
    public SaResult handlerException(NotRoleException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return SaResult.error("缺少角色：" + e.getRole());
    }


    // 拦截：二级认证校验失败异常
    @ExceptionHandler(NotSafeException.class)
    public SaResult handlerException(NotSafeException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return SaResult.error("二级认证校验失败：" +e.getMessage());  //  e.getService()  目前不支持这个版本
    }


    // 拦截：服务封禁异常
    @ExceptionHandler(DisableServiceException.class)
    public SaResult handlerException(DisableServiceException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return SaResult.error("当前账号 " + e.getService() + " 服务已被封禁 (level=" + e.getLevel() + ")：" + e.getDisableTime() + "秒后解封");
    }






    /**
     * 1、服务调用
     */

    // 拦截：http超时
    @ExceptionHandler(value = ResourceAccessException.class)
    public GlobalEntity exceptionHandler(ResourceAccessException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "ResourceAccessException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：http缺少入参
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public GlobalEntity exceptionHandler(HttpMessageNotReadableException e)
    {
        e.printStackTrace();
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "HttpMessageNotReadableException,当前功能不可用，请稍后再试", "");
    }

    //

    /**
     * 2、数据库
     */

    // 拦截：数据库连接失败异常
    @ExceptionHandler(value = DataAccessException.class)
    public GlobalEntity exceptionHandler(DataAccessException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(), e.getMessage(), ",当前功能不可用，请稍后再试", "", null);
    }

    //

    /**
     * Java
     */

    // 拦截：空指针异常
    @ExceptionHandler(NullPointerException.class)
    public GlobalEntity handlerException(NullPointerException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "NullPointerException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：BeanCreationException
    @ExceptionHandler(BeanCreationException.class)
    public GlobalEntity handlerException(BeanCreationException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "BeanCreationException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：ConnectException
    @ExceptionHandler(ConnectException.class)
    public GlobalEntity handlerException(ConnectException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "ConnectException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：类型转换异常
    @ExceptionHandler(ClassCastException.class)
    public GlobalEntity handlerException(ClassCastException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "ClassCastException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：数组越界异常
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public GlobalEntity handlerException(ArrayIndexOutOfBoundsException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "ArrayIndexOutOfBoundsException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：数字格式异常
    @ExceptionHandler(NumberFormatException.class)
    public GlobalEntity handlerException(NumberFormatException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "NumberFormatException,当前功能不可用，请稍后再试", "");
    }

    // 拦截：日期格式异常
    @ExceptionHandler(IllegalArgumentException.class)
    public GlobalEntity handlerException(IllegalArgumentException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "IllegalArgumentException,当前功能不可用，请稍后再试", "");
    }

    // 请求入参异常-MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalEntity handlerException(MethodArgumentNotValidException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "MethodArgumentNotValidException,当前功能不可用，请稍后再试", "");
    }

    // 请求入参异常-MethodArgumentNotValidException
    @ExceptionHandler(MismatchedInputException.class)
    public GlobalEntity handlerException(MismatchedInputException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "MismatchedInputException,当前功能不可用，请稍后再试", "");
    }

    // 请求入参异常-DuplicateKeyException
    @ExceptionHandler(DuplicateKeyException.class)
    public GlobalEntity handlerException(DuplicateKeyException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "DuplicateKeyException,当前功能不可用，请稍后再试", "");
    }


    // 用户异常
    @ExceptionHandler(value = UserException.class)
    public GlobalEntity handlerException(UserException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,e.getErrorCode(), e.getErrorMessage(), "UserException,当前功能不可用，请稍后再试", "");
    }

    // 业务异常
    @ExceptionHandler(value = BusinessException.class)
    public GlobalEntity handlerException(BusinessException e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,e.getErrorCode(), e.getErrorMessage(), "UserException,当前功能不可用，请稍后再试", "");
    }


    // aop/反射异常
    @ExceptionHandler(value = UndeclaredThrowableException.class)
    public GlobalEntity handlerException(UndeclaredThrowableException e)
    {
        Throwable undeclaredThrowable = e.getUndeclaredThrowable();

        // 如果是用户异常
        if(undeclaredThrowable instanceof UserException)
        {
            UserException userException = (UserException)undeclaredThrowable;
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            return GlobalEntity.error(null,userException.getErrorCode(), userException.getErrorMessage(), "UserException,当前功能不可用，请稍后再试", "");
        }

        // 如果是其他异常

        //
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(),e.getMessage(),"UndeclaredThrowableException,用户功能受限，请稍后重试~","");
    }


    // 拦截：其它所有异常
    @ExceptionHandler(Exception.class)
    public GlobalEntity handlerException(Exception e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "Exception,当前功能不可用，请稍后再试", "");
    }


    // 拦截：顶级
    @ExceptionHandler(Throwable.class)
    public GlobalEntity handlerException(Throwable e)
    {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        return GlobalEntity.error(null,GlobalCodeEnum.ERROR.getCode(), e.getMessage(), "Throwable,当前功能不可用，请稍后再试", "");
    }


}
