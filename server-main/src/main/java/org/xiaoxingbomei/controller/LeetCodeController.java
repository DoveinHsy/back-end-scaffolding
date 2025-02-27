package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.LeetCodeService;

@RestController
@Tag(name = "leetcode controller",description = "leetcode controller")
public class LeetCodeController
{
    @Autowired
    private LeetCodeService leetCodeService;

    @Operation(summary = "leetCode practice", description = "easy-1")
    @RequestMapping(value = ApiConstant.LeetCode.easy_1,method = RequestMethod.POST)
    public GlobalEntity easy_1(@RequestBody String paramString)
    {
        GlobalEntity ret = leetCodeService.easy_1(paramString);
        return ret;
    }

    @Operation(summary = "leetCode practice", description = "easy-136")
    @RequestMapping(value = ApiConstant.LeetCode.easy_136,method = RequestMethod.POST)
    public GlobalEntity easy_136(@RequestBody String paramString)
    {
        GlobalEntity ret = leetCodeService.easy_136(paramString);
        return ret;
    }

    @Operation(summary = "leetCode practice", description = "easy-1")
    @RequestMapping(value = ApiConstant.LeetCode.easy_206,method = RequestMethod.POST)
    public GlobalEntity easy_206(@RequestBody String paramString)
    {
        GlobalEntity ret = leetCodeService.easy_206(paramString);
        return ret;
    }


}
