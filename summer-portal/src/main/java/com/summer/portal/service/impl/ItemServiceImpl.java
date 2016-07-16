package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.HttpClientUtil;
import com.summer.common.utils.JsonUtils;
import com.summer.pojo.TbItem;
import com.summer.pojo.TbItemDesc;
import com.summer.pojo.TbItemParamItem;
import com.summer.portal.pojo.PortalItem;
import com.summer.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by toy on 7/15/16.
 */
@Service
public class ItemServiceImpl implements ItemService{

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_ITEM_BASE_URL}")
    private String REST_ITEM_BASE_URL;
    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;
    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public TbItem getItemById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_BASE_URL + itemId);
        SummerResult summerResult = SummerResult.formatToPojo(json, PortalItem.class);
        TbItem item = (TbItem)summerResult.getData();
        return item;
    }

    @Override
    public String getItemDescById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + itemId);
        // json -> pojo
        SummerResult summerResult = SummerResult.formatToPojo(json, TbItemDesc.class);
        TbItemDesc itemDesc = (TbItemDesc)summerResult.getData();
        String desc = itemDesc.getItemDesc();
        return desc;
    }

    @Override
    public String getItemParamById(Long itemId) {
        String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + itemId);
        SummerResult summerResult=SummerResult.formatToPojo(json, TbItemParamItem.class);
        TbItemParamItem itemParamItem = (TbItemParamItem)summerResult.getData();
        String paramJson = itemParamItem.getParamData();
        List<Map> paramList = JsonUtils.jsonToList(paramJson, Map.class);

        //generate html
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        sb.append("<tbody>\n");
        for (Map map: paramList) {
            sb.append("<tr>\n");
            sb.append("<th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th></tr><tr>\n");
            sb.append("</tr>");
            List<Map> mapList2 = (List<Map>) map.get("params");
            for(Map map2: mapList2) {
                sb.append("<tr>\n");
                sb.append("<td class=\"tdTitle\">" + map2.get("k")+ "</td>\n");
                sb.append("<td>" + map2.get("v") + "</td>\n");
                sb.append("</tr>\n");

            }
        }
        sb.append("</tbody>");
        sb.append("</table>");

        return sb.toString();
    }
}
