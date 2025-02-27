package org.xiaoxingbomei.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.constant.ApiConstant;
import org.xiaoxingbomei.service.WikiService;

/**
 * 知识库
 */
@RestController
public class WikiController
{
    @Autowired
    private WikiService wikiService;


    // =====================================================================================================

    @Operation(summary = "wiki",description = "个人-知识库-列表查询")
    @RequestMapping(value = ApiConstant.Wiki.mine_knowledgeBases_search,method = RequestMethod.POST)
    public GlobalEntity mine_knowledgeBases_search(@RequestBody String paramString)
    {
        wikiService.mine_knowledgeBases_search(paramString);
        return null;
    }

    @Operation(summary = "wiki",description = "个人-知识库-指定查询")
    @RequestMapping(value = ApiConstant.Wiki.mine_knowledgeBase_search,method = RequestMethod.POST)
    public GlobalEntity mine_knowledgeBase_search(@RequestBody String paramString)
    {
        wikiService.mine_knowledgeBase_search(paramString);
        return null;
    }

    @Operation(summary = "wiki",description = "个人-知识库-创建")
    @RequestMapping(value = ApiConstant.Wiki.mine_knowledgeBase_create,method = RequestMethod.POST)
    public GlobalEntity mine_knowledgeBase_create(@RequestBody String paramString)
    {
        wikiService.mine_knowledgeBase_create(paramString);
        return null;
    }

    @Operation(summary = "wiki",description = "个人-知识库-批量创建")
    @RequestMapping(value = ApiConstant.Wiki.mine_knowledgeBase_multiCreate,method = RequestMethod.POST)
    public GlobalEntity mine_knowledgeBase_multiCreate(@RequestBody String paramString)
    {
        wikiService.mine_knowledgeBase_multiCreate(paramString);
        return null;
    }
}
