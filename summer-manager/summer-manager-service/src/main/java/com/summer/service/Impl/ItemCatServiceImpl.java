package com.summer.service.Impl;

import com.summer.common.pojo.EasyUITreeNode;
import com.summer.mapper.TbItemCatMapper;
import com.summer.pojo.TbItemCat;
import com.summer.pojo.TbItemCatExample;
import com.summer.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toy on 6/8/16.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EasyUITreeNode> getItemCatList(long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria =  example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);

        List<EasyUITreeNode> resultList = new ArrayList<>();
        for (TbItemCat tbItemCat: list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setStatus(tbItemCat.getIsParent()?"closed": "open");

            resultList.add(node);
        }
        return resultList;
    }
}
