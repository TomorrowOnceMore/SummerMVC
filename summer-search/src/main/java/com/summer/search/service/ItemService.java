package com.summer.search.service;

import com.summer.common.pojo.SummerResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by toy on 7/9/16.
 */
public interface ItemService {
    SummerResult importItems() throws IOException, SolrServerException;
}
