package com.summer.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.summer.mapper.TbItemMapper;
import com.summer.pojo.TbItem;
import com.summer.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by toy on 6/5/16.
 */
public class TestPageHelper {

    @Test
    public void testPageHelper() throws Exception {
        //get mapper agent obj
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        TbItemMapper itemMapper = (TbItemMapper) applicationContext.getBean(TbItemMapper.class);
        // set page
        PageHelper.startPage(1, 30);
        // exec query
        TbItemExample example = new TbItemExample();
        List<TbItem> list = itemMapper.selectByExample(example);
        // get page result
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        long total = pageInfo.getTotal();
        System.out.println(total);
        int pages = pageInfo.getPages();
        System.out.println(pages);
        int pageSize = pageInfo.getPageSize();
        System.out.println(pageSize);
    }

}
