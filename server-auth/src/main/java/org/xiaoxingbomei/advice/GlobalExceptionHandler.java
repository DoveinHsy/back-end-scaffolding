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

import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;

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


}
