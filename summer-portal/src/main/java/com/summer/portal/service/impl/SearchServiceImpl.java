package com.summer.portal.service.impl;

import com.summer.common.pojo.SearchResult;
import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.HttpClientUtil;
import com.summer.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toy on 7/10/16.
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String keyword, int page, int rows) {
        Map<String, String> param = new HashMap<>();
        param.put("keyword", keyword);
        param.put("page", page + "");
        param.put("rows", rows + "");

        String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
        //转换成Java对象
        SummerResult summerResult = SummerResult.formatToPojo(json, SearchResult.class);
        SearchResult searchResult = (SearchResult)summerResult.getData();
        return searchResult;
    }
}
