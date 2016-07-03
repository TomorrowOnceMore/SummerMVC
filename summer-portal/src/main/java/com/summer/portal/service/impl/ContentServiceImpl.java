package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.HttpClientUtil;
import com.summer.common.utils.JsonUtils;
import com.summer.pojo.TbContent;
import com.summer.portal.pojo.AdNode;
import com.summer.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_AD1_CID}")
    private String REST_CONTENT_AD1_CID;

    @Value("${REST_CONTENT_URL}")
    private String REST_CONTENT_URL;
    @Override
    public String getAd1List() {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_URL + REST_CONTENT_AD1_CID);
        // json to java
        SummerResult summerResult = SummerResult.formatToList(json, TbContent.class);
        List<TbContent> contentList = (List<TbContent>) summerResult.getData();
        List<AdNode> resultList = new ArrayList<>();
        for (TbContent tbContent: contentList) {
            AdNode node = new AdNode();
            node.setHeight(240);
            node.setWidth(670);
            node.setSrc(tbContent.getPic());
            node.setHeightB(240);
            node.setWidthB(670);
            node.setSrcB(tbContent.getPic2());
            node.setAlt(tbContent.getSubTitle());
            node.setHref(tbContent.getUrl());

            resultList.add(node);
        }
        // resultList to json data
        String resultJson = JsonUtils.objectToJson(resultList);
        return resultJson;
    }
}
