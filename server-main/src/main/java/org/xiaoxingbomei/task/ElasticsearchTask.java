package org.xiaoxingbomei.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.xiaoxingbomei.dao.localhost.UserMapper;
import org.xiaoxingbomei.service.UserService;
import org.xiaoxingbomei.vo.User;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ElasticsearchTask
{

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    // =======================================================

    // 每小时的第0分钟执行一次
    @Scheduled(cron = "0 0 * * * ?")
    public void updateLocalDictionary() throws IOException
    {
        log.info("开始执行词典更新任务");

        // 1. 数据收集
        List<String> data = userMapper.getAllUserInfo().stream().map(User::getName).collect(Collectors.toList());

        // 2. 新词发现
        List<String> newWords = userService.discoverNewWords(data);

        // 3. 本地词典更新
        userService.updateLocalDictionary(newWords);

        // 4. 远程词典更新
//        userService.updateRemoteDictionary(newWords);

        log.info("词典更新任务完成");
    }
}
