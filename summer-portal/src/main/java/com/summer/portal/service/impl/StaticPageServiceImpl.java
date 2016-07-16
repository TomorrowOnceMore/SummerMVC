package com.summer.portal.service.impl;

import com.summer.common.pojo.SummerResult;
import com.summer.pojo.TbItem;
import com.summer.portal.service.ItemService;
import com.summer.portal.service.StaticPageService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by toy on 7/16/16.
 */
@Service
public class StaticPageServiceImpl implements StaticPageService {

    @Value("${STATIC_PAGE_PATH}")
    private String STATIC_PAGE_PATH;

    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public SummerResult genItemHtml(Long itemId) throws IOException, TemplateException {
        // item -> data
        TbItem item = itemService.getItemById(itemId);
        String itemDesc = itemService.getItemDescById(itemId);
        String itemParam = itemService.getItemParamById(itemId);
        // gen html
        freemarker.template.Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("item.ftl");
        Map root = new HashMap<>();
        root.put("item", item);
        root.put("itemDesc", itemDesc);
        root.put("itemParam", itemParam);
        Writer out = new FileWriter(new File(STATIC_PAGE_PATH + itemId + ".html"));
        template.process(root, out);
        out.flush();
        out.close();
        return SummerResult.ok();
    }
}
