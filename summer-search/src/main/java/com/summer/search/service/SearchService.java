package com.summer.search.service;

import com.summer.common.pojo.SearchResult;

/**
 * Created by toy on 7/9/16.
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;
}
