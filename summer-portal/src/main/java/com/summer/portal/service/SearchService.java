package com.summer.portal.service;

import com.summer.common.pojo.SearchResult;

/**
 * Created by toy on 7/10/16.
 */
public interface SearchService {
    SearchResult search(String keyword, int page, int rows);
}
