package org.xiaoxingbomei.utils;//package org.xiaoxingbomei.utils;
//
//import com.google.common.hash.Funnels;
//import com.google.common.hash.Hashing;
//import com.google.common.primitives.Longs;
//import org.xiaoxingbomei.config.redis.JedisResourcePool;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.Pipeline;
//
//import java.nio.charset.Charset;
//
//public class BloomFilter_Utils {
//
//    private static final String BF_KEY_PREFIX = "bf_";
//
//    private long numApproxElements;
//    private double falseProbability;
//    // hash个数
//    private int numHashFunctions;
//    // 数组长度
//    private int bitmapLength;
//
//    private JedisResourcePool jedisResourcePool;
//
//    /**
//     * 构造布隆过滤器。注意：在同一业务场景下，三个参数务必相同
//     *
//     * @param numApproxElements 预估元素数量
//     * @param fpp               可接受的最大误差
//     * @param jedisResourcePool Codis专用的Jedis连接池
//     */
//    public BloomFilter_Utils(Long numApproxElements, double fpp, JedisResourcePool jedisResourcePool) {
//        this.numApproxElements = numApproxElements;
//        this.falseProbability = fpp;
//        this.jedisResourcePool = jedisResourcePool;
//        // 数组长度 m = (n * lnp)/ln2^2
//        bitmapLength = (int) (-numApproxElements * Math.log(fpp) / (Math.log(2) * Math.log(2)));
//        // hash个数 k = (n / m ) * ln2
//        numHashFunctions = Math.max(1, (int) Math.round((double) bitmapLength / numApproxElements * Math.log(2)));
//    }
//
//    /**
//     * 取得预估元素数量
//     */
//    public long getExpectedInsertions() {
//        return numApproxElements;
//    }
//
//    /**
//     * 返回元素存在的错误概率
//     */
//    public double getFalseProbability() {
//        return falseProbability;
//    }
//
//    /**
//     * 取得自动计算的最优哈希函数个数
//     */
//    public int getNumHashFunctions() {
//        return numHashFunctions;
//    }
//
//    /**
//     * 取得自动计算的最优Bitmap长度
//     */
//    public int getBitmapLength() {
//        return bitmapLength;
//    }
//
//    /**
//     * 计算一个元素值哈希后映射到Bitmap的哪些bit上
//     *
//     * @param element 元素值
//     * @return bit下标的数组
//     */
//    private long[] getBitIndices(String element) {
//        long[] indices = new long[numHashFunctions];
//
//        // 元素  使用MurMurHash3 128位Hash算法转换值
//        byte[] bytes = Hashing.murmur3_128()
//                .hashObject(element, Funnels.stringFunnel(Charset.forName("UTF-8")))
//                .asBytes();
//
//        // 低8位转Long值
//        long hash1 = Longs.fromBytes(
//                bytes[7], bytes[6], bytes[5], bytes[4], bytes[3], bytes[2], bytes[1], bytes[0]
//        );
//        // 高8位转Long值
//        long hash2 = Longs.fromBytes(
//                bytes[15], bytes[14], bytes[13], bytes[12], bytes[11], bytes[10], bytes[9], bytes[8]
//        );
//
//        long combinedHash = hash1;
//        // 双重哈希进行散列
//        for (int i = 0; i  <= numHashFunctions; i++) {
//            indices[i] = (combinedHash & Long.MAX_VALUE) % bitmapLength;
//            combinedHash += hash2;
//        }
//        return indices;
//    }
//
//
//    /**
//     * 插入元素
//     *
//     * @param key       原始Redis键，会自动加上'bf_'前缀
//     * @param element   元素值，字符串类型
//     * @param expireSec 过期时间（秒）
//     */
//    public void add(String key, String element, int expireSec) {
//        if (key == null || element == null) {
//            throw new RuntimeException("键值均不能为空");
//        }
//        String actualKey = BF_KEY_PREFIX.concat(key);
//
//        try (Jedis jedis = jedisResourcePool.getResource()) {
//            try (Pipeline pipeline = jedis.pipelined()) {
//                // 遍历元素所有hash结果的bit位置
//                for (long index : getBitIndices(element)) {
//                    pipeline.setbit(actualKey, index, true);
//                }
//                pipeline.syncAndReturnAll();
//            }
//            jedis.expire(actualKey, expireSec);
//        }
//    }
//
//    /**
//     * 检查元素在集合中是否（可能）存在
//     *
//     * @param key     原始Redis键，会自动加上'bf_'前缀
//     * @param element 元素值，字符串类型
//     */
//    public boolean contains(String key, String element) {
//        if (key == null || element == null) {
//            throw new RuntimeException("键值均不能为空");
//        }
//        String actualKey = BF_KEY_PREFIX.concat(key);
//        boolean result = false;
//
//        try (Jedis jedis = jedisResourcePool.getResource()) {
//            // 遍历元素所有hash结果的bit位置
//            try (Pipeline pipeline = jedis.pipelined()) {
//                for (long index : getBitIndices(element)) {
//                    pipeline.getbit(actualKey, index);
//                }
//                result = !pipeline.syncAndReturnAll().contains(false);
//            }
//        }
//        return result;
//    }
//
////    public static void main(String[] args) {
////        String path = Path.getCurrentPath() + "/config/zzjodis.properties";
////        ConfigReadUtil configReadUtil = new ConfigReadUtil(path);
////        try {
////            JedisResourcePool jedisResourcePool = RoundRobinJedisPool.
////                    create()
////                    .curatorClient(configReadUtil.getString("jodisZkStr"), 5000)
////                    .zkProxyDir(configReadUtil.getString("zkProxyDir"))
////                    .team(configReadUtil.getString("team"))
////                    .connectionTimeoutMs(configReadUtil.getInt("connectionTimeoutMs"))
////                    .soTimeoutMs(configReadUtil.getInt("soTimeoutMs"))
////                    .appKey(configReadUtil.getString("appKey"))
////                    .password("".equals(configReadUtil.getString("password")) ? null : configReadUtil.getString("password"))
////                    .build();
////            BloomFilterUtils bloomFilterUtils = new BloomFilterUtils(10000, 0.01, jedisResourcePool);
////            bloomFilterUtils.add("filter01", "element001", 30 * 60);
////            System.out.println(bloomFilterUtils.contains("filter01", "element001"));  // true
////            System.out.println(bloomFilterUtils.contains("filter01", "element002"));  // false
////        } catch (Exception e) {
////            Exception_Utils.recursiveReversePrintStackCauseCommon(e);
////        }
////    }
//
//}