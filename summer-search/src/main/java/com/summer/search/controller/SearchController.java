package com.summer.search.controller;

import com.summer.common.pojo.SearchResult;
import com.summer.common.pojo.SummerResult;
import com.summer.common.utils.ExceptionUtil;
import com.summer.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by toy on 7/9/16.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;


    @RequestMapping("/q")
    @ResponseBody
    public SummerResult search(@RequestParam(defaultValue = "")String keyword,
                               @RequestParam(defaultValue = "1")Integer page,
                               @RequestParam(defaultValue = "30")Integer rows) {
        try {
            //转换字符集
            keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");

            SearchResult searchResult = searchService.search(keyword, page, rows);
            return SummerResult.ok(searchResult);
        } catch (Exception e) {
            e.printStackTrace();
            return SummerResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }
}
