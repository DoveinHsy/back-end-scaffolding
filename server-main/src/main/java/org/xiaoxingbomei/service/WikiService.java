package org.xiaoxingbomei.service;

import org.xiaoxingbomei.common.entity.GlobalEntity;

public interface WikiService
{
    GlobalEntity mine_knowledgeBases_search(String paramString); //
    GlobalEntity mine_knowledgeBase_search(String paramString);  //
    GlobalEntity mine_knowledgeBase_create(String paramString);  //
    GlobalEntity mine_knowledgeBase_multiCreate(String paramString);  //
}
