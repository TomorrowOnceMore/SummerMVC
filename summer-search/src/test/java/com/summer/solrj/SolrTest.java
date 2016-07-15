package com.summer.solrj;


import org.apache.log4j.net.SyslogAppender;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * Created by toy on 7/8/16.
 */
public class SolrTest {

    @Test
    public void testSolrJ() throws Exception {
        SolrClient solrClient = new HttpSolrClient("http://172.21.14.118:8080/solr/new_core");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "solrtest01");
        document.addField("item_title", "测试商品");
        document.addField("item_sell_point", "卖点");
        // add index lib
        solrClient.add(document);
        solrClient.commit();
    }

    @Test
    public void testQuery() throws Exception {
        SolrClient solrClient = new HttpSolrClient("http://172.21.14.118:8080/solr/new_core");
        SolrQuery query = new SolrQuery();
        // filed: key
        query.setQuery("*:*");
        QueryResponse response = solrClient.query(query);
        SolrDocumentList solrDocumentList= response.getResults();
        for (SolrDocument solrDocument: solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
        }
    }

    @Test
    public void testSolrCloud() throws Exception {
        CloudSolrClient solrClient = new CloudSolrClient("172.21.14.118:2181 172.21.14.118:2182 172.21.14.118:2183");
        //set default collection
        solrClient.setDefaultCollection("new_core2");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "test01");
        document.addField("item_title", "title1");
        solrClient.add(document);
        solrClient.commit();
    }
}
