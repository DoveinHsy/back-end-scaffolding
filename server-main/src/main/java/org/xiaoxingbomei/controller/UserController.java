package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.annotation.ServiceSwitch;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.constant.Constant;
import org.xiaoxingbomei.service.UserService;

import javax.servlet.http.HttpServletResponse;


/**
 * 用户级别controller
 */
@RestController
@Tag(name="用户controller",description = "用户controller")
public class UserController
{

    @Autowired
    private UserService userService;

    // =========================================================================

    @ServiceSwitch(switchKey = Constant.SwitchConfigCode.XX_SWITCH)
    @RequestMapping("/testDesensitization")
    public GlobalEntity testDesensitization(@RequestBody String paramString)
    {
//        User user = User.builder().
//                idCard("411321199502051515").
//                phone("15628409697").
//                bankCard("6215594200001119631").
//                address("浙江省杭州市上城区").build();

//        return GlobalEntity.success(user.toString(),"","","","");
        return null;
    }

    @Operation(summary = "获取全部用户信息",description = "获取全部用户信息（redis+es+mysql）")
    @RequestMapping(value = ApiConstant.User.getAllUserInfo, method = RequestMethod.POST)
    public GlobalEntity getAllUserInfo(@RequestBody String paramString)
    {
        GlobalEntity ret = userService.getAllUserInfo();
        return ret;
    }

    @Operation(summary = "获取用户excel模板",description = "通过fastexcel生成用户实体对应的excel模板文件")
    @RequestMapping(value = ApiConstant.User.exportUserInfoTemplate, method = RequestMethod.POST)
    public void exportUserInfoTemplate(HttpServletResponse response) throws Exception
    {
        userService.exportUserInfoTemplate(response);
    }

    @Operation(summary = "通过excel导入批量更新用户信息，无则新增，有则更新",description = "通过excel导入批量更新用户信息，无则新增，有则更新")
    @RequestMapping(value = ApiConstant.User.updateUserInfoByTemplate, method = RequestMethod.POST)
    public GlobalEntity updateUserInfoByTemplate(MultipartFile file) throws Exception
    {
        GlobalEntity ret = userService.updateUserInfoByTemplate(file);
        return ret;
    }

    @Operation(summary = "构建用户索引（es）",description = "构建用户索引（es）")
    @RequestMapping(value = ApiConstant.User.createUserIndex, method = RequestMethod.POST)
    public void createUserIndex(@RequestBody String paramString) throws Exception
    {
        userService.createUserIndex();
    }


}
