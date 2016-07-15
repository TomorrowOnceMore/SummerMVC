package com.summer.search.service.impl;

import com.summer.common.pojo.SearchItem;
import com.summer.common.pojo.SummerResult;
import com.summer.search.mapper.ItemMapper;
import com.summer.search.service.ItemService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by toy on 7/9/16.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private SolrClient solrClient;
    @Autowired
    private ItemMapper itemMapper;


    @Override
    public SummerResult importItems() throws IOException, SolrServerException {
        //查询数据库获得商品列表
        List<SearchItem> itemList = itemMapper.getItemList();

        for (SearchItem searchItem: itemList) {
            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImages());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            //写入索引库
            solrClient.add(document);
        }
        //必须提交
        solrClient.commit();
        return SummerResult.ok();
    }
}
