package org.xiaoxingbomei.service.implement;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.FastExcel;
import cn.idev.excel.write.metadata.style.WriteCellStyle;
import cn.idev.excel.write.style.HorizontalCellStyleStrategy;
import cn.idev.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import org.springframework.web.multipart.MultipartFile;
import org.xiaoxingbomei.aspect.ControllerLogAspectByPath;
import org.xiaoxingbomei.common.entity.GlobalEntity;
import org.xiaoxingbomei.config.Thread.DynamicThreadPool;
import org.xiaoxingbomei.config.mybatis.MybatisLogInterceptor;
import org.xiaoxingbomei.dao.localhost.UserMapper;
import org.xiaoxingbomei.entity.GlobalRequestContext;
import org.xiaoxingbomei.service.ServerEsFeignClient;
import org.xiaoxingbomei.service.UserService;
import org.xiaoxingbomei.utils.Exception_Utils;
import org.xiaoxingbomei.vo.User;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService
{


    @Autowired
    UserMapper userMapper;

    @Autowired
    RestHighLevelClient elasticsearchClient;

    @Autowired
    DynamicThreadPool dynamicThreadPool;

    private String localDictionaryPath = "D:\\Environment\\elasticsearch-7.10.2\\plugins\\ik\\config\\custom\\mydict.dic";

    @Autowired
    private ServerEsFeignClient serverEsFeignClient;


    // =========================================================================


    public UserServiceImpl() throws IOException
    {
        // 初始化时加载词典内容到缓存
        loadDictionaryCache();
    }


    @Override
    public GlobalEntity getAllUserInfo()
    {
        // 从数据库中获取全部用户信息
        List<User> allUserInfo = userMapper.getAllUserInfo();

        // 构建结果返参
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("allUserInfo", allUserInfo);
        return GlobalEntity.success(resultMap, "获取全部用户信息");
    }

    @Override
    public void createUserIndex()
    {
        /**
         * 以下是每个字段映射类型的说明和设计依据：
         *
         * 1. country: "type": "keyword"
         * 设计缘由：country 字段通常用于精确匹配（如查询特定国家）。keyword 类型适用于不需要分词的字段，主要用于精确查询和聚合。
         * 2. address: "analyzer": "ik_smart_analyzer", "type": "text"
         * 设计缘由：address 字段可能包含较长的文本，且需要分词，以便进行模糊搜索和匹配。text 类型适用于分析（分词）的字段，并且使用 ik_smart_analyzer 分词器来处理中文文本。
         * 3. occupation: "type": "keyword"
         * 设计缘由：occupation 字段是一个分类字段（例如“医生”、“工程师”），不需要分词，只需要精确匹配。keyword 类型适合用于精确查询、聚合和排序。
         * 4. gender: "type": "keyword"
         * 设计缘由：gender 字段也是一个分类字段（如“男”和“女”），通常用于精确匹配，keyword 类型适合这种用途。
         * 5. bankCard: "type": "keyword"
         * 设计缘由：bankCard 字段用于存储银行卡号码，通常需要进行精确查询。由于银行卡号是一个唯一值，并且不需要分词，keyword 类型最为合适。
         * 6. city: "type": "keyword"
         * 设计缘由：city 字段用于存储城市名，通常用于精确查询和聚合。使用 keyword 类型避免了不必要的分词处理。
         * 7. idCard: "type": "keyword"
         * 设计缘由：idCard 字段存储身份证号码，通常进行精确匹配。使用 keyword 类型避免了分词，并支持高效查询。
         * 8. mobileNumber: "type": "keyword"
         * 设计缘由：mobileNumber 存储手机号码，作为精确值查询字段，应该使用 keyword 类型，避免分词的干扰。
         * 9. companyName: "analyzer": "ik_smart_analyzer", "type": "text"
         * 设计缘由：companyName 字段需要进行分词处理以支持模糊搜索和匹配。text 类型配合 ik_smart_analyzer 分词器，可以处理中文公司名称的搜索需求。
         * 10. updateTime: "format": "yyyy-MM-dd HH:mm:ss||epoch_millis", "type": "date"
         * 设计缘由：updateTime 是时间字段，通常用于排序和范围查询。使用 date 类型并指定格式，可以有效支持时间范围查询和排序。
         * 11. avatar: "type": "keyword"
         * 设计缘由：avatar 字段通常存储头像的 URL 或文件名，通常用于精确查询。使用 keyword 类型适合此场景。
         * 12. phoneNumber: "type": "keyword"
         * 设计缘由：phoneNumber 字段存储电话号码，通常用于精确查询，使用 keyword 类型更为合适。
         * 13. nationality: "type": "keyword"
         * 设计缘由：nationality 是一个分类字段（例如“美国”、“中国”），用于精确匹配。使用 keyword 类型适用于此类字段。
         * 14. createTime: "format": "yyyy-MM-dd HH:mm:ss||epoch_millis", "type": "date"
         * 设计缘由：createTime 字段用于记录创建时间。date 类型是用于时间字段的最佳选择，可以有效支持范围查询和排序。
         * 15. name: "analyzer": "ik_smart_analyzer", "type": "text"
         * 设计缘由：name 字段需要进行分词，以便支持模糊查询和匹配（例如，部分匹配姓名）。使用 text 类型和 ik_smart_analyzer 分词器是适合中文姓名的做法。
         * 16. state: "type": "keyword"
         * 设计缘由：state 字段通常用于标识状态或区域（例如，“已完成”、“未处理”）。因为这些字段不需要分词处理，只需要精确匹配，适合使用 keyword 类型。
         * 17. userType: "type": "keyword"
         * 设计缘由：userType 是一个分类字段（如“管理员”、“普通用户”），不需要分词，使用 keyword 类型更合适。
         * 18. maritalStatus: "type": "keyword"
         * 设计缘由：maritalStatus 字段通常是一个简单的分类字段（如“已婚”、“未婚”），适合使用 keyword 类型进行精确匹配。
         * 19. status: "type": "keyword"
         * 设计缘由：status 字段表示对象或数据的状态（例如“启用”、“禁用”），通常是一个固定的值，适合使用 keyword 类型进行精确匹配。
         * 总结：
         * keyword 类型：用于精确匹配的字段，适用于那些需要精确查询、排序和聚合的字段，不进行分词。
         * text 类型：用于全文搜索的字段，适合那些需要分词处理的文本数据，支持模糊搜索和匹配。配合合适的分词器（如 ik_smart_analyzer）来处理中文文本。
         * date 类型：用于存储日期和时间数据，支持时间范围查询和排序。
         * 通过这种设计，可以确保不同类型的数据在 Elasticsearch 中得到高效和准确的处理。
         */
        // 定义索引的设置和映射
        String mappingJson = "{\n" +
                "  \"settings\": {\n" +
                "    \"analysis\": {\n" +
                "      \"analyzer\": {\n" +
                "        \"ik_smart_analyzer\": {\n" +
                "          \"type\": \"custom\",\n" +
                "          \"tokenizer\": \"ik_smart\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"country\": { \"type\": \"keyword\" },\n" +
                "      \"address\": { \"analyzer\": \"ik_smart_analyzer\", \"type\": \"text\" },\n" +
                "      \"occupation\": { \"type\": \"keyword\" },\n" +
                "      \"gender\": { \"type\": \"keyword\" },\n" +
                "      \"bankCard\": { \"type\": \"keyword\" },\n" +
                "      \"city\": { \"type\": \"keyword\" },\n" +
                "      \"idCard\": { \"type\": \"keyword\" },\n" +
                "      \"mobileNumber\": { \"type\": \"keyword\" },\n" +
                "      \"companyName\": { \"analyzer\": \"ik_smart_analyzer\", \"type\": \"text\" },\n" +
                "      \"updateTime\": { \"format\": \"yyyy-MM-dd HH:mm:ss||epoch_millis\", \"type\": \"date\" },\n" +
                "      \"avatar\": { \"type\": \"keyword\" },\n" +
                "      \"phoneNumber\": { \"type\": \"keyword\" },\n" +
                "      \"nationality\": { \"type\": \"keyword\" },\n" +
                "      \"createTime\": { \"format\": \"yyyy-MM-dd HH:mm:ss||epoch_millis\", \"type\": \"date\" },\n" +
                "      \"name\": { \"analyzer\": \"ik_smart_analyzer\", \"type\": \"text\" },\n" +
                "      \"state\": { \"type\": \"keyword\" },\n" +
                "      \"userType\": { \"type\": \"keyword\" },\n" +
                "      \"maritalStatus\": { \"type\": \"keyword\" },\n" +
                "      \"status\": { \"type\": \"keyword\" }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        // 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("user_info");
        request.source(mappingJson, XContentType.JSON);
        // 执行索引创建请求
        try {
            CreateIndexResponse createIndexResponse = elasticsearchClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Transactional
    @Override
    public void createUserInfo(List<User> users)
    {
        userMapper.createUserInfo(users);
    }

    @Override
public void exportUserInfoTemplate(HttpServletResponse response) throws Exception
    {
    // 设置响应头
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder.encode("UserInfoTemplate", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

    // 模拟数据（可以为空，仅生成表头）
    List<User> users = new ArrayList<>();

    // 设置表头样式
    WriteCellStyle headWriteCellStyle = new WriteCellStyle();
    headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); // 背景颜色
//        headWriteCellStyle.setFont(new Font(16, true)); // 字体大小和加粗

    // 设置内容样式
    WriteCellStyle contentWriteCellStyle = new WriteCellStyle();

    // 注册样式策略
    HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);

    // 生成 Excel 文件并写入响应流
    EasyExcel.write(response.getOutputStream(), User.class)
            .registerWriteHandler(styleStrategy) // 应用样式
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动调整列宽
            .sheet("用户信息")
            .doWrite(users);
}

    @Override
    public GlobalEntity updateUserInfoByTemplate(MultipartFile file) throws Exception
    {

        StopWatch stopWatch = new StopWatch();
        // 1、提取excel中信息
        stopWatch.start("parseExcelToUsers");
        List<User> userList = new ArrayList<>();
        try
        {
            userList = parseExcelToUsers(file);
            log.info("使用fastexcel读取excel成功");
        } catch (Exception e)
        {
            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
            log.info("使用fastexcel接收excel文件失败:{}",e.getMessage());
        }
        stopWatch.stop();

        // 2、数据校验
        // 2-1. 提取所有身份证号
        Set<String> importIdCardSet = userList.stream()
                .map(User::getIdCard)
                .collect(Collectors.toSet());

        // 2-2. 批量查询已存在的身份证号
        List<User> existingUsers = userMapper.findByIdCards(new ArrayList<>(importIdCardSet));

        // 将 existingUsers 转换为 Map，键是 idCard，值是 User 对象
        Map<String, User> existingUserMap = existingUsers.stream()
                .collect(Collectors.toMap(User::getIdCard, user -> user));

        Set<String> existingIdCards = existingUsers.stream()
                .map(User::getIdCard)
                .collect(Collectors.toSet());

        // 2-3. 区分 createUserList 和 updateUserList
        List<User> createUserList = new ArrayList<>();
        List<User> updateUserList = new ArrayList<>();

        for (User user : userList)
        {
            if (existingIdCards.contains(user.getIdCard()))
            {
                // 如果用户已存在，设置原先的 id
                User existingUser = existingUserMap.get(user.getIdCard());
                user.setId(existingUser.getId()); // 设置原先的 id
                updateUserList.add(user); // 添加到更新列表
            } else
            {
                // 如果用户不存在，生成新的 id
                user.setId(UUID.randomUUID().toString()); // 生成新的 id
                createUserList.add(user); // 添加到新增列表
            }
        }
        log.info("要新增的用户数量：{}，\n要更新的用户数量：{}",createUserList.size(),updateUserList.size());

        // 3、落库(存量更新、新增插入)
        stopWatch.start("Database Insert");
        batchInsertUsers(createUserList,50);
        batchUpdateUsers(updateUserList,50);
        stopWatch.stop();

        // 4、同步es
        stopWatch.start("Elasticsearch Insert");
        syncUserInfoToEsAsync(createUserList);
        syncUserInfoToEsAsync(updateUserList);
        stopWatch.stop();

        // 5、创建结果体
        log.info(stopWatch.prettyPrint());
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        return GlobalEntity.success(resultMap,"批量更新用户成功");
    }

    public void batchInsertUsers(List<User> userList, int batchSize) {
        int totalSize = userList.size();
        int batchCount = (totalSize + batchSize - 1) / batchSize; // 计算批次数量
        log.info("计算批次数量：{}", batchCount);

        // 获取主线程的 requestContext
        GlobalRequestContext requestContext = ControllerLogAspectByPath.getGlobalRequestContext();
        log.info("requestContext:{}", requestContext);

        CountDownLatch latch = new CountDownLatch(batchCount); // 用于等待所有批次完成

        for (int i = 0; i < batchCount; i++)
        {
            int fromIndex = i * batchSize;
            int toIndex = Math.min(fromIndex + batchSize, totalSize);
            List<User> batchList = userList.subList(fromIndex, toIndex); // 获取当前批次的数据

            log.info("batchList: {}", batchList);

            // 提交任务到线程池
            dynamicThreadPool.execute(() ->
            {
                // 将主线程的 requestContext 设置到子线程中
                ControllerLogAspectByPath.setGlobalRequestContext(requestContext);
                try
                {
                    userMapper.createUserInfo(batchList); // 执行插入操作
                } catch (Exception e)
                {
                    log.error("插入数据失败，异常信息: {}", e.getMessage(), e);
                } finally
                {
                    latch.countDown(); // 任务完成，计数器减一
                    // 清除子线程的 requestContext
                    ControllerLogAspectByPath.clearGlobalRequestContext();
                }
            });
        }
        try
        {
            latch.await(); // 等待所有批次完成
        } catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException("多线程插入数据被中断", e);
        }
    }
    public void batchUpdateUsers(List<User> userList, int batchSize) {
        int totalSize = userList.size();
        int batchCount = (totalSize + batchSize - 1) / batchSize; // 计算批次数量
        log.info("计算批次数量：{}", batchCount);

        // 获取主线程的 requestContext
        GlobalRequestContext requestContext = ControllerLogAspectByPath.getGlobalRequestContext();
        log.info("requestContext:{}", requestContext);

        CountDownLatch latch = new CountDownLatch(batchCount); // 用于等待所有批次完成

        for (int i = 0; i < batchCount; i++)
        {
            int fromIndex = i * batchSize;
            int toIndex = Math.min(fromIndex + batchSize, totalSize);
            List<User> batchList = userList.subList(fromIndex, toIndex); // 获取当前批次的数据

            log.info("batchList: {}", batchList);

            // 提交任务到线程池
            dynamicThreadPool.execute(() ->
            {
                // 将主线程的 requestContext 设置到子线程中
                ControllerLogAspectByPath.setGlobalRequestContext(requestContext);
                try
                {
                    userMapper.updateUserInfo(batchList); // 执行插入操作
                } catch (Exception e)
                {
                    log.error("插入数据失败，异常信息: {}", e.getMessage(), e);
                } finally
                {
                    latch.countDown(); // 任务完成，计数器减一
                    // 清除子线程的 requestContext
                    ControllerLogAspectByPath.clearGlobalRequestContext();
                }
            });
        }
        try
        {
            latch.await(); // 等待所有批次完成
        } catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
            throw new RuntimeException("多线程插入数据被中断", e);
        }
    }

    // 使用 FastExcel 解析上传的excel文件
    private List<User> parseExcelToUsers(MultipartFile file) throws Exception
    {
        // 创建InputStream对象读取MultipartFile内容
        try (InputStream inputStream = file.getInputStream())
        {
            // 使用fastexcel将excel转换为List<User>
            List<User> users = FastExcel.read(inputStream)
                    .head(User.class)
                    .sheet()
                    .doReadSync();

            return users;
        }
    }

    // 异步执行 Elasticsearch 数据同步
    @Async
    public void syncUserInfoToEsAsync(List<User> users)
    {
        // 将数据转换为 Elasticsearch 所需的格式
        List<Map<String, Object>> userDocList = transformToEsFormat(users);

        // 批量同步到 Elasticsearch
        GlobalEntity saveResult = serverEsFeignClient.saveUserInfoToEs(userDocList);
        log.info("批量同步到 Elasticsearch:{}",saveResult.getMessage());
    }

    // 将数据转换为 Elasticsearch 格式
    private List<Map<String, Object>> transformToEsFormat(List<User> users)
    {
        // 这里根据具体的需求转换数据为 Elasticsearch 的文档格式
        return users.stream()
                .map(user ->
                {
                    Map<String, Object> docMap = new HashMap<>();
                    docMap.put("id", user.getId());
                    docMap.put("name", user.getName());
                    docMap.put("gender", user.getGender());
                    docMap.put("idCard", user.getIdCard());
                    docMap.put("avatar", user.getAvatar());
                    docMap.put("phoneNumber", user.getPhoneNumber());
                    docMap.put("mobileNumber", user.getMobileNumber());
                    docMap.put("address", user.getAddress());
                    docMap.put("nationality", user.getNationality());
                    docMap.put("country", user.getCountry());
                    docMap.put("state", user.getState());
                    docMap.put("city", user.getCity());
                    docMap.put("bankCard", user.getBankCard());
                    docMap.put("occupation", user.getOccupation());
                    docMap.put("companyName", user.getCompanyName());
                    docMap.put("maritalStatus", user.getMaritalStatus());
                    docMap.put("userType", user.getUserType());
                    docMap.put("createTime", user.getCreateTime());
                    docMap.put("updateTime", user.getUpdateTime());
                    docMap.put("status", user.getStatus());
                    // 添加其他字段的映射...
                    return docMap;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<String> discoverNewWords(List<String> texts) {
    // 使用 dynamicThreadPool 提交每个文本的关键词提取任务
    List<Future<List<String>>> futures = texts.stream()
            // 对每个文本进行 HanLP 关键词提取，返回一个 Future 对象
            .map(text -> dynamicThreadPool.submit(() -> HanLP.extractKeyword(text, 10)))
            .collect(Collectors.toList()); // 将所有 Future 对象收集到一个列表中

    // 对所有 Future 结果进行处理
    return futures.stream()
            // 对每个 Future 对象进行处理，尝试获取其计算结果
            .flatMap(future ->
            {
                try {
                    // 获取结果并将其转换为流，再进行合并
                    return future.get().stream();
                } catch (Exception e) {
                    // 如果获取结果时发生异常，则抛出运行时异常
                    throw new RuntimeException(e);
                }
            })
            // 使用 distinct 去重，确保返回的词语列表没有重复项
            .distinct()
            // 收集最终的去重词语列表
            .collect(Collectors.toList());
    }


    // 内存中的词典缓存
    private Set<String> dictionaryCache = new HashSet<>();

    // 加载词典内容到缓存
    private void loadDictionaryCache() throws IOException {
        log.info("apollo.elasticsearch.local.dictionary.path:{}", localDictionaryPath);
        Path path = Paths.get(localDictionaryPath);
        if (Files.exists(path)) {
            dictionaryCache = new HashSet<>(Files.readAllLines(path));
    }
}

    @Override
    public void updateLocalDictionary(List<String> newWords) {
        log.info("开始更新词典，新词数量：{}", newWords.size());

    // 去重：只添加词典中不存在的新词
    Set<String> uniqueNewWords = new HashSet<>(newWords);
    uniqueNewWords.removeAll(dictionaryCache);

    // 将新词追加到文件
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(localDictionaryPath, true))) {
        for (String word : uniqueNewWords) {
            writer.write(word);
            writer.newLine();
        }
    } catch (Exception e) {
        Exception_Utils.recursiveReversePrintStackCauseCommon(e);
        log.error("词典更新失败：{}", e.getMessage());
    }

    // 更新缓存
    dictionaryCache.addAll(uniqueNewWords);

    log.info("词典更新完成，新增词语数量：{}", uniqueNewWords.size());

}





}
