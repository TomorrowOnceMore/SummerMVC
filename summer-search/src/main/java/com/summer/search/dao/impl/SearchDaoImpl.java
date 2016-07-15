package com.summer.search.dao.impl;

import com.summer.common.pojo.SearchItem;
import com.summer.common.pojo.SearchResult;
import com.summer.search.dao.SearchDao;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by toy on 7/9/16.
 */
@Repository
public class SearchDaoImpl implements SearchDao {

    @Autowired
    private SolrClient solrClient;

    @Override
    public SearchResult search(SolrQuery query) throws IOException, SolrServerException {
        // query
        QueryResponse response = solrClient.query(query);
        SolrDocumentList solrDocumentList = response.getResults();
        List<SearchItem> itemList = new ArrayList<>();
        for (SolrDocument solrDocument: solrDocumentList) {
            SearchItem item = new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            item.setImages((String) solrDocument.get("item_image"));
            item.setPrice((Long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));

            //高亮
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            item.setTitle((String) solrDocument.get("item_title"));
            itemList.add(item);
        }
        SearchResult result = new SearchResult();
        result.setItemList(itemList);
        //结果总数量
        result.setRecordCount(solrDocumentList.getNumFound());
        return result;
    }
}
