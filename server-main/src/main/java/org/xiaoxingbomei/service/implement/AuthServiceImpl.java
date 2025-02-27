package org.xiaoxingbomei.service.implement;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.xiaoxingbomei.Enum.GlobalCodeEnum;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.dto.SystemAuthDto;
import org.xiaoxingbomei.service.AuthService;
import org.xiaoxingbomei.utils.Redis_Utils;
import org.xiaoxingbomei.common.utils.Request_Utils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService
{

    @Autowired
    private RedisTemplate  redisTemplate;

    @Autowired
    private Redis_Utils redis_utils;

    private static final String CAPTCHA_PREFIX = "captcha_";    // 验证码存于reids的前缀
    private static final long CAPTCHA_EXPIRATION = 5;          // 验证码过期时间


    @Override
    public GlobalEntity generateCaptcha(String paramString)
    {
        // 获取手机号
        String phone = Request_Utils.getParam(paramString, "phone");

        // 校验验证码系统可用性（暂用redis）,验证码系统不可用的时候
        if(!redis_utils.isRedisConnected())
        {
            return GlobalEntity.error("生成验证码失败，redis暂不可用");
        }

        // 生成验证码（这里真实情况下应该接入外部接口获取验证码）
        String captcha = String.valueOf(new Random().nextInt(999999));

        // 将生成验证码存于缓存
        redisTemplate.opsForValue().set(CAPTCHA_PREFIX+phone,captcha,CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        //
        return GlobalEntity.success("生成验证码成功，对应的手机号为："+phone);
    }

    @Override
    public GlobalEntity validateCaptcha(String paramString)
    {
        // 获取手机号
        String phone    = Request_Utils.getParam(paramString, "phone");
        String captcha  = Request_Utils.getParam(paramString, "captcha");

        // 校验验证码系统可用性（暂用redis）
        if(!redis_utils.isRedisConnected())
        {
            return GlobalEntity.error("校验验证码失败，redis暂不可用");
        }

        // 校验验证码的对错，正确的话 消费掉
        String storedCaptcha = (String) redisTemplate.opsForValue().get(CAPTCHA_PREFIX + phone);
        if(StringUtils.equals(storedCaptcha,captcha ))
        {
            redisTemplate.delete(CAPTCHA_PREFIX + phone);
            return GlobalEntity.success("校验验证码成功,手机号为"+phone);
        }

        //
        return GlobalEntity.error("校验验证码失败，验证码不匹配");
    }

    /**
     * doLogin
     */
    @Override
    public GlobalEntity doLogin(String paramString)
    {
        //
        String userId   = Request_Utils.getParam(paramString, "userId");
        String auth     = Request_Utils.getParam(paramString, "auth");

        // 此处用代码替代真实情况下数据库比对操作
        if(StringUtils.equals("ysx",userId) & StringUtils.equals("ysx123",auth))
        {
            StpUtil.login(10001);
            return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(),"登录成功","",null);
        }

        return GlobalEntity.error(GlobalCodeEnum.ERROR.getCode(), GlobalCodeEnum.ERROR.getMessage(), "登录失败","",null);
    }



    /**
     * isLogin
     */
    @Override
    public GlobalEntity isLogin(String paramString)
    {
        boolean login = StpUtil.isLogin();
        return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(),GlobalCodeEnum.SUCCESS.getMessage(),"","","是否登录:"+login);
    }

    /**
     * checkLogin
     */
    @Override
    public GlobalEntity checkLogin(String paramString)
    {
        StpUtil.checkLogin();
        return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(), "","","checkLogin down");
    }
    /**
     * tokenInfo
     */
    @Override
    public GlobalEntity tokenInfo(String paramString)
    {
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        SaResult tokenInfoResult = SaResult.data(tokenInfo);
        return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(), "","",tokenInfoResult.getMsg());
    }

    /**
     * logOut
     */
    @Override
    public GlobalEntity logOut(String paramString)
    {
        StpUtil.logout();
        return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(), GlobalCodeEnum.SUCCESS.getMessage(), "","",SaResult.ok().getMsg());
    }

    /**
     *
     */
    @Override
    public GlobalEntity getPermissionList(SystemAuthDto dto)
    {
        List<String> permissionList = StpUtil.getPermissionList();
        return GlobalEntity.success(GlobalCodeEnum.SUCCESS.getCode(),GlobalCodeEnum.SUCCESS.getMessage(), "获取权限list成功","",permissionList.toString());
    }
    @Override
    public GlobalEntity hasPermissionList(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity checkPermissionList(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity checkPermissionAnd(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity CheckPermissionOr(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity getRoleList(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity hasRole(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity checkRole(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity checkRoleAnd(SystemAuthDto dto) {
        return null;
    }

    @Override
    public GlobalEntity checkRoleOr(SystemAuthDto dto) {
        return null;
    }


}
