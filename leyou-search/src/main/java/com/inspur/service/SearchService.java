package com.inspur.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inspur.GoodsRepository;
import com.inspur.common.entity.PageResult;
import com.inspur.entity.Goods;
import com.inspur.client.BrandClient;
import com.inspur.client.CategoryClient;
import com.inspur.client.GoodsClient;
import com.inspur.client.SpecificationClient;
import com.inspur.entity.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: leyou
 * @description: No Description
 * @author: Yang jian wei
 * @create: 2019-08-15 11:37
 */
@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;
    
    @Autowired
    private GoodsClient goodsClient;
    
    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public Goods buildGoods(Spu spu) throws IOException {
        Goods goods = new Goods();
        //根据分类Ids 查询 分类的名称
        List<String> names = this.categoryClient.queryCategoryNamesByIds(Arrays.asList(spu.getCid1(), spu.getCid2(),spu.getCid3()));

        //根据品牌Id 查询品牌
        Brand brand = this.brandClient.queryBrandById(spu.getBrandId());

        //查询有所sku
        List<Sku> skuList = this.goodsClient.querySkuListBySpuId(spu.getId());
        //构建所有的price
        List<Long> prices = new ArrayList<Long>();
        //搜集Sku的必要字段信息的
        List<Map<String, Object>> skuMapList = new ArrayList<Map<String, Object>>();


        skuList.forEach(sku -> {
            prices.add(sku.getPrice());
            Map<String, Object> map = new HashMap<>();
            map.put("id", sku.getId());
            map.put("title", sku.getTitle());
            map.put("price", sku.getPrice());
            //获取sku 中的图片,数据库中图片可能是多张以逗号分隔,所以这里我们只获取第一张
            map.put("image", StringUtils.isNotBlank(sku.getImages()) ? StringUtils.split(sku.getImages(), ",")[0] : "");
            skuMapList.add(map);
        });

        //根据spu中的cid3查询出所有的搜索规格参数
        List<SpecParam> specParams = this.specificationClient.queryParamList(null, spu.getCid3(), null, true);

        /**
         * 获取所有规格参数的值
         */
        SpuDetail spuDetail = this.goodsClient.querySpuDetailBySpuId(spu.getId());
        //通用的规格参数的值的配置
        Map<String, Object> genericParamMap = OBJECT_MAPPER.readValue(spuDetail.getGenericSpec(), new TypeReference<Map<String, Object>>() {});
        //特殊的规格参数的值的配置
        Map<String, List<Object>> specialParamMap = OBJECT_MAPPER.readValue(spuDetail.getSpecialSpec(), new TypeReference<Map<String, List<Object>>>() {});

        Map<String, Object> lastSpecMap = new HashMap<>();
        specParams.forEach(specParam -> {
            //判断规格参数的类型是否是通用类型的规格参数
            if(specParam.getGeneric()) {
                //通用规格参数的设置值
                String value = genericParamMap.get(specParam.getId().toString()).toString();
                if (specParam.getNumeric()) {
                    chooseSegment(value, specParam);
                }
                lastSpecMap.put(specParam.getName(), value);
            } else {
                //特殊规格参数的配置
                List<Object> value = specialParamMap.get(specParam.getId().toString());
                lastSpecMap.put(specParam.getName(), value);
            }
        });


        goods.setId(spu.getId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setBrandId(spu.getBrandId());
        goods.setCreateTime(spu.getCreateTime());
        goods.setSubTitle(spu.getSubTitle());
        //all 字段 spu标题 需要分类名称 和 品牌名称
        goods.setAll(spu.getTitle() + " " + StringUtils.join(names, " ") + " " + brand.getName());

        //获取sku下的所有的 price,然后进行拼接
        goods.setPrice(prices);

        //获取spu下的所有 sku 并转化为 json字符串
        goods.setSkus(OBJECT_MAPPER.writeValueAsString(skuMapList));

        //获取所有需要查询的规格参数 通用和特殊
        goods.setSpecs(lastSpecMap);

        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public SearchResult searchGoodsByPage(SearchRequest request) {
        if(StringUtils.isEmpty(request.getKey())) {
            return null;
        }

        //自定义查询构建器:
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        BoolQueryBuilder basicQueryBuilder = buildBoolQueryBuilder(request);


        //抽取出基本查询;
//        MatchQueryBuilder basicQueryBuilder = QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND);

        //添加查询条件
        queryBuilder.withQuery(basicQueryBuilder);
        //添加分页
        queryBuilder.withPageable(PageRequest.of(request.getPage() - 1, request.getSize()));
        //添加结果集的过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "subTitle", "skus"}, null));

        //执行结果集前,聚合分类和品牌的名称
        String aggCategoryName = "categories";
        String aggBrandName = "brands";

        queryBuilder.addAggregation(AggregationBuilders.terms(aggCategoryName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(aggBrandName).field("brandId"));

        //执行查询获取结果集
        AggregatedPage<Goods> aggregatedPage =
                (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        List<Map<String, Object>> categories =
                getCategoryAggResult(aggregatedPage.getAggregation(aggCategoryName));

        List<Brand> brands =
                getBrandAggResult(aggregatedPage.getAggregation(aggBrandName));

        SearchResult searchResult =
                new SearchResult(aggregatedPage.getTotalElements(),
                        aggregatedPage.getTotalPages(),
                        aggregatedPage.getContent(),
                        categories,
                        brands);

        //规格参数做聚合,只有在一个分类的时候再进行聚合
        if(!CollectionUtils.isEmpty(categories) && categories.size() == 1) {
            //对规格参数进行聚
            List<Map<String, Object>> specs = getSpecParamsAggResult(Long.valueOf(categories.get(0).get("id").toString()), basicQueryBuilder);
            searchResult.setSpecs(specs);
        }

        return searchResult;
    }

    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest request) {
        BoolQueryBuilder boolQueryBuilder =  QueryBuilders.boolQuery();
        //添加基本查询条件
        MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", request.getKey()).operator(Operator.AND);
        boolQueryBuilder.must(basicQuery);
        //获取过滤条件
        Map<String, String> filter = request.getFilter();
        filter.forEach((key , value) -> {
            String filterField = "";
            if("品牌".equals(key)) {
                filterField = "brandId";
            } else if("分类".equals(key)) {
                filterField = "cid3";
            } else {
                filterField = "specs." + key + ".keyword";
            }
            boolQueryBuilder.filter(QueryBuilders.termQuery(filterField, value));
        });

        return boolQueryBuilder;
    }

    /**
     * 根据查询条件,聚合规格参数
     * @param cid
     * @param basicQuery
     * @return
     */
    private List<Map<String, Object>> getSpecParamsAggResult(Long cid, QueryBuilder basicQuery) {
        List<Map<String, Object>> lastMapList = new ArrayList<Map<String, Object>>();

        //自定义查询构建器:
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加查询条件
        queryBuilder.withQuery(basicQuery);

        //查询要聚合的规格参数
        List<SpecParam> specParams = this.specificationClient.queryParamList(null, cid, null, true);

        //添加规格参数的聚合
        specParams.forEach(specParam -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(specParam.getName()).field("specs." + specParam.getName() + ".keyword"));

        });

        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));

        //执行聚合查询
        AggregatedPage<Goods> aggregatedPage = (AggregatedPage<Goods>) this.goodsRepository.search(queryBuilder.build());

        //解析聚合结果集
        Map<String, Aggregation> stringAggregationMap = aggregatedPage.getAggregations().asMap();

        stringAggregationMap.forEach((name, agg) -> {

            Map<String, Object> map = new HashMap<>();
            map.put("k", name);

            List<String> options = new ArrayList<>();
            StringTerms stringTerms = (StringTerms) agg;
            stringTerms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options", options);

            lastMapList.add(map);
        });

        return lastMapList;

    }

    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        LongTerms longTerms = (LongTerms) aggregation;
        return longTerms.getBuckets().stream().map(bucket -> {
            Long brandId = bucket.getKeyAsNumber().longValue();
            System.out.println("brandId ------------------------------------------------->:" + brandId);
            return this.brandClient.queryBrandById(brandId);
        }).collect(Collectors.toList());
    }

    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        LongTerms longTerms = (LongTerms) aggregation;
        return longTerms.getBuckets().stream().map(bucket -> {
            Long cid3 = bucket.getKeyAsNumber().longValue();
            Map<String, Object> map = new HashMap<>();
            List<String> name = this.categoryClient.queryCategoryNamesByIds(Arrays.asList(cid3));
            map.put("id", cid3);
            map.put("name", name.get(0));
            return map;
        }).collect(Collectors.toList());
    }

    public void createIndex(Long id) throws IOException {

        Spu spu = this.goodsClient.getSpuBySpuId(id);
        // 构建商品
        Goods goods = this.buildGoods(spu);

        // 保存数据到索引库
        this.goodsRepository.save(goods);
    }

    public void deleteIndex(Long id) {
        this.goodsRepository.deleteById(id);
    }
}
