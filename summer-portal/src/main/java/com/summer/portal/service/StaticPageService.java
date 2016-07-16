package com.summer.portal.service;

import com.summer.common.pojo.SummerResult;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * Created by toy on 7/16/16.
 */
public interface StaticPageService {
    SummerResult genItemHtml(Long itemId) throws IOException, TemplateException;
}
