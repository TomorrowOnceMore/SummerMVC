package com.summer.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.IdUtils;
import com.summer.common.utils.JsonUtils;
import com.summer.mapper.TbItemDescMapper;
import com.summer.mapper.TbItemMapper;
import com.summer.mapper.TbItemParamItemMapper;
import com.summer.pojo.*;
import com.summer.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by toy on 6/4/16.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Autowired
    private TbItemDescMapper itemDescMapper;

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem getItemById(long itemid) {
        //itemMapper.selectByPrimaryKey(itemid);
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemid);
        List<TbItem> list =
        itemMapper.selectByExample(example);
        TbItem item = null;
        if (list!= null && list.size() > 0) {
            item = list.get(0);
        }
        return item;
    }

    //查询物品
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public SummerResult createItem(TbItem item, String desc, String itemParam) {
        long itemId = IdUtils.genItemId();
        //补全属性
        item.setId(itemId);
        // 1 normal 2 outdate 3 deletion
        item.setStatus((byte)1);

        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);

        itemMapper.insert(item);
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        // insert date
        itemDescMapper.insert(itemDesc);

        // item param
        TbItemParamItem itemParamItem= new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(date);
        itemParamItem.setUpdated(date);

        itemParamItemMapper.insert(itemParamItem);

        return SummerResult.ok();
    }

    @Override
    public String getItemParamHtml(Long itemid) {
        //查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemid);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list == null || list.isEmpty()) {
            return "";
        }
        TbItemParamItem itemParamItem = list.get(0);
        //json data
        String paramData = itemParamItem.getParamData();
        List<Map> mapList = JsonUtils.jsonToList(paramData, Map.class);
        //generate html
        StringBuffer sb = new StringBuffer();
       sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
       sb.append("<tbody>\n");
        for (Map map: mapList) {
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
