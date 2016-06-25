package com.summer.rest.service.impl;

import com.summer.mapper.TbItemCatMapper;
import com.summer.pojo.TbItem;
import com.summer.pojo.TbItemCat;
import com.summer.pojo.TbItemCatExample;
import com.summer.rest.pojo.CatNode;
import com.summer.rest.pojo.ItemCatResult;
import com.summer.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类列表查询
 * Created by toy on 6/25/16.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public ItemCatResult getItemCatList() {
        List catList = getItemCatList(0l);
        ItemCatResult result = new ItemCatResult();
        result.setData(catList);
        return result;
    }

    private List getItemCatList(Long parentId) {
        //根据parentID查询列表
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // result
        List <TbItemCat> list = itemCatMapper.selectByExample(example);
        List resultList = new ArrayList<>();
        int index = 0;
        for (TbItemCat tbItemCat: list) {
            if (index >= 14) {
                break;
            }
            if (tbItemCat.getIsParent()) {
                CatNode node = new CatNode();
                node.setUrl("/products/" + tbItemCat.getId() + ".html");
                if (tbItemCat.getParentId() == 0) {
                    node.setName("<a href='/products/" + tbItemCat.getId() +".html'>" + tbItemCat.getName() + "</a>");
                    index++;
                } else {
                    node.setName(tbItemCat.getName());
                }
                node.setItems(getItemCatList(tbItemCat.getId()));
                resultList.add(node);
            } else {
                String item = "/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName();
                resultList.add(item);
            }
        }
        return resultList;
    }
}
