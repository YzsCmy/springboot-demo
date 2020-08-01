package com.unionman.springbootdemo.repository.impl;

import com.alibaba.fastjson.JSONObject;
import com.unionman.springbootdemo.entity.StudentExaminationInfo;
import com.unionman.springbootdemo.repository.StudentExaminationInfoRepository;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.zxp.esclientrhl.enums.AggsType;
import org.zxp.esclientrhl.repository.ElasticsearchTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author maoyong.cui
 * @version 1.0
 * @date 2020/7/30
 */
@Repository
public class StuExaminationInfoRepository implements StudentExaminationInfoRepository {
    @Autowired
    private ElasticsearchTemplate<StudentExaminationInfo,String> elasticsearchTemplate;

    @Override
    public void addStudentExaminationInfo(List<StudentExaminationInfo> infos) {
        try {
            elasticsearchTemplate.save(infos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public StudentExaminationInfo findInfoByName(String name) {
        QueryBuilder termQuery = QueryBuilders.termQuery("name", name);
        try {
            List<StudentExaminationInfo> infoList = elasticsearchTemplate.search(termQuery, StudentExaminationInfo.class);
            if(!infoList.isEmpty()){
                return infoList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findInfoByNameFuzzy(String name) {
        QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("name", name);
        try {
            List<StudentExaminationInfo> infoList = elasticsearchTemplate.search(queryBuilder, StudentExaminationInfo.class);
            return infoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findInfoByAgeRange(Integer begin, Integer end) {
        QueryBuilder queryBuilder =QueryBuilders.rangeQuery("age").from(begin).to(end);
        try {
            return elasticsearchTemplate.search(queryBuilder,StudentExaminationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findBothNearSighted() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        NestedQueryBuilder left = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.rightEye").lt(4.0), ScoreMode.None);
        NestedQueryBuilder right = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.leftEye").lt(4.0), ScoreMode.None);
        boolQueryBuilder.must(left).must(right);
        try {
            return elasticsearchTemplate.search(boolQueryBuilder,StudentExaminationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findNearSighted() {
        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        BoolQueryBuilder boolQueryBuilder3 = QueryBuilders.boolQuery();
        NestedQueryBuilder leftLo = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.rightEye").lt(4.0), ScoreMode.None);
        NestedQueryBuilder rightLo = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.leftEye").lt(4.0), ScoreMode.None);
        NestedQueryBuilder leftHi = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.rightEye").gt(4.0), ScoreMode.None);
        NestedQueryBuilder rightHi = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.leftEye").gt(4.0), ScoreMode.None);
        boolQueryBuilder1.must(leftLo).must(rightHi);
        boolQueryBuilder2.must(leftHi).must(rightLo);
        boolQueryBuilder3.should(boolQueryBuilder1).should(boolQueryBuilder2);
        try {
            return elasticsearchTemplate.search(boolQueryBuilder3,StudentExaminationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findNotNearSighted() {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        NestedQueryBuilder left = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.rightEye").gte(4.0), ScoreMode.None);
        NestedQueryBuilder right = QueryBuilders.nestedQuery("vision", QueryBuilders.rangeQuery("vision.leftEye").gte(4.0), ScoreMode.None);
        boolQueryBuilder.must(left).must(right);
        try {
            return elasticsearchTemplate.search(boolQueryBuilder,StudentExaminationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StudentExaminationInfo> findByMeasurementsRange(Integer low, Integer high) {
        //BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        NestedQueryBuilder queryBuilder = QueryBuilders.nestedQuery("posture", QueryBuilders.rangeQuery("posture.measurements").from(low).to(high), ScoreMode.None);
        try {
            return elasticsearchTemplate.search(queryBuilder,StudentExaminationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Long> statisticByClassName() {
        try {
            Map map = elasticsearchTemplate.aggs("name", AggsType.count,null,StudentExaminationInfo.class,"fclass");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Double> statisticByHeight() {
        NestedAggregationBuilder builder = AggregationBuilders.nested("mytag", "posture");
        builder.subAggregation(AggregationBuilders.sum("sumheight").field("posture.height"));
        builder.subAggregation(AggregationBuilders.avg("avgheight").field("posture.height"));
        builder.subAggregation(AggregationBuilders.max("maxheight").field("posture.height"));
        builder.subAggregation(AggregationBuilders.min("minheight").field("posture.height"));

        try {
            Aggregations aggs = elasticsearchTemplate.aggs(builder, QueryBuilders.matchAllQuery(), StudentExaminationInfo.class);

            Map<String, Aggregation> asMap = aggs.getAsMap();
            Aggregation mytag = asMap.get("mytag");
            String s = JSONObject.toJSONString(mytag);
            JSONObject jsonObject = JSONObject.parseObject(s);

            JSONObject jsonAggs = jsonObject.getJSONObject("aggregations").getJSONObject("asMap");
            Double maxheight = ((BigDecimal) jsonAggs.getJSONObject("maxheight").get("value")).doubleValue();
            Double sumheight = ((BigDecimal) jsonAggs.getJSONObject("sumheight").get("value")).doubleValue();
            Double avgheight = ((BigDecimal) jsonAggs.getJSONObject("avgheight").get("value")).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
            Double minheight = ((BigDecimal) jsonAggs.getJSONObject("minheight").get("value")).doubleValue();
            HashMap<String, Double> resMap = new HashMap<>();
            resMap.put("minHeight",minheight);
            resMap.put("maxHeight",maxheight);
            resMap.put("avgHeight",avgheight);
            resMap.put("sumHeight",sumheight);
            System.out.println(resMap);
            return resMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
