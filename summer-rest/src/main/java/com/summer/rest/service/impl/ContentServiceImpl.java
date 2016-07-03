package com.summer.rest.service.impl;

import com.summer.mapper.TbContentMapper;
import com.summer.pojo.TbContent;
import com.summer.pojo.TbContentExample;
import com.summer.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public List<TbContent> getContentList(Long cid) {

        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);

        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        return list;
    }
}
