package com.zhenxuan.tradeapi.thirdparty;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.google.common.collect.ImmutableMap;
import com.zhenxuan.tradeapi.dao.entity.GoodsSpuItem;
import com.zhenxuan.tradeapi.dao.entity.Item;
import com.zhenxuan.tradeapi.dao.entity.ShopItem;
import com.zhenxuan.tradeapi.utils.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * aws dynamodb services
 * http://docs.amazonaws.cn/amazondynamodb/latest/developerguide/DynamoDBMapper.OptionalConfig.html
 */
@Component
public class DynamoDbService {

    private static final Logger logger = LoggerFactory.getLogger(DynamoDbService.class);

    @Value("${aws.endpoint}")
    private String awsEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.access.key}")
    private String accessKeyId;

    @Value("${aws.secret.key}")
    private String secretKeyId;

    private AmazonDynamoDB client;

    private DynamoDBMapper mapper;

    public DynamoDbService() {
    }

    @PostConstruct
    public void init() {
        // region和endpoint只能配一个
        if ((StringUtils.isEmpty(awsEndpoint) && StringUtils.isEmpty(awsRegion)) ||
                (!StringUtils.isEmpty(awsEndpoint) && !StringUtils.isEmpty(awsRegion))) {
            logger.error("aws config option endpoint and region error");
            throw new RuntimeException("aws configuration error");
        }

        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretKeyId);
        AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .withClientConfiguration(new ClientConfiguration().withRequestTimeout(5000));
                // .withMetricsCollector(new MyCustomMetricsCollector())
                // .withRequestHandlers(new MyCustomRequestHandler(), new MyOtherCustomRequestHandler)
        if (!StringUtils.isEmpty(awsRegion)) {
            builder.withRegion(awsRegion);
        } else {
            builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsEndpoint, awsRegion));
        }

        client = builder.build();
        mapper = new DynamoDBMapper(client);
    }

    @PreDestroy
    public void destroy() {
        if (client != null) {
            client.shutdown();
            client = null;
        }
    }

    /**
     * 按主键读取
     * @param clazz
     * @param hashKey
     * @param <T>
     * @return
     */
    public <T extends Item> T read(Class<T> clazz, Object hashKey, boolean consistentRead) {
        if (consistentRead) {
            // dynamodb 一致性读
            return mapper.load(clazz, hashKey, new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
        } else {
            return mapper.load(clazz, hashKey);
        }
    }

    /**
     * 按主键和排序键读取
     * @param clazz
     * @param hashKey
     * @param rangeKey
     * @param consistentRead
     * @param <T>
     * @return
     */
    public <T extends Item> T read(Class<T> clazz, Object hashKey, Object rangeKey, boolean consistentRead) {
        if (consistentRead) {
            // dynamodb 一致性读
            return mapper.load(clazz, hashKey, rangeKey, new DynamoDBMapperConfig(DynamoDBMapperConfig.ConsistentReads.CONSISTENT));
        } else {
            return mapper.load(clazz, hashKey, rangeKey);
        }
    }

    /**
     * 批量读取
     * @param items
     * @param <T>
     * @return
     */
    public <T extends Item> Map<String, List<Object>> read(List<T> items) {
        return mapper.batchLoad(items);
    }

//    public <T> T load(T keyObj) {
//        return mapper.load(keyObj);
//    }

    /**
     * 单条写入
     * @param obj
     * @param <T>
     */
    public <T extends Item> void write(T obj) {
        mapper.save(obj);

        // TODO:dynamodb  强制忽略版本
//        mapper.write(item,
//                new DynamoDBMapperConfig(
//                        DynamoDBMapperConfig.SaveBehavior.CLOBBER));
    }

    /**
     * 批量写入，不保证事务
     * @param objs
     * @param <T>
     */
    public <T extends Item> void write(List<T> objs) {
        mapper.batchSave(objs);
        //mapper.batchWrite()
    }

    public <T extends Item> void delete(T obj) {
        mapper.delete(obj);
    }

    // TODO: dynamodb count

    /**
     * 批量删除，可能部分成功
     * @param items
     * @param <T>
     */
    public <T extends Item> void delete(List<T> items) {
        mapper.batchDelete(items);
    }

    /**
     * 排序键比较码
     */
    public enum QueryOp {
        GreaterThen(">"),
        GreaterThenOrEqualTo(">="),
        EqualTo("="),
        LessThen("<"),
        LessThenOrEqualTo("<="),
        BeginWith("begins_with");

        QueryOp(String op) {
            this.op = op;
        }

        private final String op;

        @Override
        public String toString() {
            return this.op;
        }
    }

    public <T extends Item> List<T> query(Class<T> clazz, T keyObj, boolean consistentRead) {
        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withHashKeyValues(keyObj)
                .withConsistentRead(consistentRead);
        return mapper.query(clazz, queryExpression);
    }

    /**
     * 按条件查询
     * @param clazz
     * @param hashKeyName
     * @param hashKeyValue
     * @param rangeKeyName
     * @param rangeKeyValue
     * @param rangeKeyOp
     * @param consistentRead
     * @param <T>
     * @return
     */
    public <T extends Item> List<T> query(Class<T> clazz, String hashKeyName, String hashKeyValue, String rangeKeyName, String rangeKeyValue, QueryOp rangeKeyOp, boolean consistentRead) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(hashKeyValue));
        eav.put(":v2",new AttributeValue().withS(rangeKeyValue));

        DynamoDBQueryExpression<T> queryExpression = new DynamoDBQueryExpression<T>()
                .withConsistentRead(consistentRead);
        switch (rangeKeyOp) {
            case BeginWith:
                queryExpression.withKeyConditionExpression(String.format("%s = :v1 and begin_with(%s, :v2)", hashKeyName, rangeKeyName));
                break;
            default:
                 queryExpression.withKeyConditionExpression(String.format("%s = :v1 and %s %s :v2", hashKeyName, rangeKeyOp.toString(), rangeKeyName));
        }
        return mapper.query(clazz, queryExpression);
    }

    // TODO: dynamodb query by index

    public static void main(String[] args) {
        DynamoDbService dynamoDbService = new DynamoDbService();

        boolean isProductEnv = false;
        if (isProductEnv) {
            dynamoDbService.accessKeyId = "AKIAP3WYFCTRFVLUYVHQ";
            dynamoDbService.secretKeyId = "fFZW57OmTX7+VUqmcs9LCCs2DYbn7kmjk3TCLMoh";
            dynamoDbService.awsRegion = "cn-northwest-1";
        } else {
            dynamoDbService.accessKeyId = "accessKeyId";
            dynamoDbService.secretKeyId = "secretKeyId";
            dynamoDbService.awsEndpoint = "http://127.0.0.1:8000";
        }
        dynamoDbService.init();


        testEnv(dynamoDbService, isProductEnv);
        testShipping(dynamoDbService);
    }

    private static void testEnv(DynamoDbService dynamoDbService, boolean isProductEnv) {
        String primaryKey = "jIWQp/Z/AABbxTr3AAAAAgE=";
        ShopItem newItem = new ShopItem();
        newItem.setUid(primaryKey);
        newItem.setName("myname" + isProductEnv);
        newItem.setBackground("mybackgroud" + isProductEnv);
        newItem.setContact("mycontact" + isProductEnv);
        newItem.setNotice("mynotice" + isProductEnv);
        newItem.setGroupQr("mygroupqr" + isProductEnv);
        List<String> gidList = new LinkedList<>();
        gidList.add("gid1" + isProductEnv);
        gidList.add("gid2" + isProductEnv);
        newItem.setGidList(gidList);

        dynamoDbService.write(newItem);

        ShopItem item = dynamoDbService.read(ShopItem.class, primaryKey, false);
        if (item == null) {
            return;
        }
        System.out.printf("shopitem=%s", item.toString());
    }

    private static void testShipping(DynamoDbService dynamoDbService) {
        GoodsSpuItem spuItem = new GoodsSpuItem();
        spuItem.setSpuId("spuid1");
        spuItem.setSkuId("0");

        List<GoodsSpuItem.SkuInfo> skuInfos = new LinkedList<>();
        GoodsSpuItem.SkuInfo skuInfo1 = new GoodsSpuItem.SkuInfo();
        skuInfo1.setSkuId("skuid1");
        skuInfo1.setSalesCount(1001);
        skuInfos.add(skuInfo1);
        GoodsSpuItem.SkuInfo skuInfo2 = new GoodsSpuItem.SkuInfo();
        skuInfo2.setSkuId("skuid2");
        skuInfo2.setSalesCount(1002);
        skuInfos.add(skuInfo2);
        spuItem.setSkuInfos(skuInfos);

        List<Map<String, Integer>> shippingInfos = new LinkedList<>();
        Map<String, Integer> shippingInfo1 = ImmutableMap.of("info1 name1", 101, "info1 name2", -1);
        shippingInfos.add(shippingInfo1);
        Map<String, Integer> shippingInfo2 = ImmutableMap.of("info2 name1", 103, "info2 name2", -1);
        shippingInfos.add(shippingInfo2);
        spuItem.setShippingInfos(shippingInfos);

        dynamoDbService.write(spuItem);

        GoodsSpuItem spuItemRead = dynamoDbService.read(GoodsSpuItem.class, "spuid1", "0", true);
        System.out.printf("%s", JsonUtil.toString(spuItemRead));
    }
}
