package com.summer.service;

import com.summer.mapper.TbItemMapper;
import com.summer.pojo.TbItem;
import com.summer.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toy on 6/4/16.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(int itemid) {
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
}
