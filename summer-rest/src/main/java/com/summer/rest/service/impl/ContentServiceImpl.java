package com.summer.rest.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.JsonUtils;
import com.summer.mapper.TbContentMapper;
import com.summer.pojo.TbContent;
import com.summer.pojo.TbContentExample;
import com.summer.rest.component.JedisClient;
import com.summer.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_CONTENT_KEY}")
    private String REDIS_CONTENT_KEY;

    @Override
    public List<TbContent> getContentList(Long cid) {
        //添加缓存
        try {
            // get cache data
            String json = jedisClient.hget(REDIS_CONTENT_KEY, cid+"");
            if (!org.apache.commons.lang3.StringUtils.isBlank(json)) {
                // json -> list
                List<TbContent>list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);

        List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
        //向缓存中添加数据
        try {
            // key = cid
            // value list -> json
            jedisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SummerResult syncContent(Long cid) {
        jedisClient.hdel(REDIS_CONTENT_KEY, cid + "");
        return SummerResult.ok();
    }
}
