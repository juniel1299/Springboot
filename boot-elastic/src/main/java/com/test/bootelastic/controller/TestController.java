package com.test.elastic;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping(value="/list")
    public String list(Model model) {

        try {

            List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

            SearchRequest searchRequest = new SearchRequest("spring");

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().size(100);

            searchSourceBuilder.query(QueryBuilders.matchAllQuery());

            searchRequest.source(searchSourceBuilder); //요청 준비 완료


            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            //검색 결과
            SearchHits searchHits = searchResponse.getHits();

            for (SearchHit hit : searchHits) {
                //hit > Document

                //Map<String,Object> map = new HashMap<>();
                Map<String,Object> map = hit.getSourceAsMap();

                map.put("id",hit.getId());

                //System.out.println(hit.getId());
                //System.out.println(hit.getSourceAsMap().get("message"));

                list.add(map);
            }

            model.addAttribute("list",list);

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "list";
    }

    @GetMapping(value="/add")
    public String add() {

        return "add";
    }

    @PostMapping(value="/addok")
    public String addok(String id, String message) {

        try {

            //자바 > 엘라스틱서치
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

            /*

                PUT spring/_doc/1
                {
                    "message": "입력받는 문장"
                }

            */

            String data = String.format("{ \"message\": \"%s\" }", message);

            IndexRequest request = new IndexRequest("spring")
                    .source(data, XContentType.JSON) //메시지
                    .setRefreshPolicy("wait_for");

            request.id(id); //문서 아이디

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);

            client.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/list";
    }

}













