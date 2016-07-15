package com.summer.search.dao;

import com.summer.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by toy on 7/9/16.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws IOException, SolrServerException;

}
