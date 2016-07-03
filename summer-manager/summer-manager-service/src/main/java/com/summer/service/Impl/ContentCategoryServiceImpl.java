package com.summer.service.Impl;

import com.summer.common.pojo.EasyUITreeNode;
import com.summer.common.pojo.SummerResult;
import com.summer.mapper.TbContentCategoryMapper;
import com.summer.pojo.TbContent;
import com.summer.pojo.TbContentCategory;
import com.summer.pojo.TbContentCategoryExample;
import com.summer.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by toy on 7/2/16.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        // parent 查询子结点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        // execute query
        List<EasyUITreeNode> resultList = new ArrayList<>();
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        for (TbContentCategory tbContentCategory: list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
            // add to list
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public SummerResult insertCategory(Long parentId, String name) {
        //pojo object
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        // 1 normal 2 deletion
        contentCategory.setStatus(1);
        contentCategory.setIsParent(false);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        //insert data
        contentCategoryMapper.insert(contentCategory);
        //取返回的主键
        Long id = contentCategory.getId();
        // isparent如果事叶子结点则更新parentid
        //查询父结点
        TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parentNode.getIsParent()) {
            parentNode.setIsParent(true);
        }
        contentCategoryMapper.updateByPrimaryKey(parentNode);
        // return key
        return SummerResult.ok(id);
    }

    @Override
    public SummerResult updateCategory(Long id, String name) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        TbContentCategory result;
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list == null || list.size() == 0)
            return SummerResult.ok();
        result = list.get(0);
        result.setName(name);
        contentCategoryMapper.updateByExample(result, example);
        return SummerResult.ok();
    }

    @Override
    public SummerResult deleteCategory(Long id) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        if (list == null || list.size() == 0)
            return SummerResult.build(404,"cannot find id");
        TbContentCategory result = list.get(0);
        if (result.getIsParent()) {
            deleteSubCategory(result.getId());
        }
        contentCategoryMapper.deleteByPrimaryKey(id);
        return SummerResult.ok();
    }

    public int deleteSubCategory(Long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

        for(TbContentCategory each: list) {
            //如果还是目录
            if (each.getIsParent()) {
                deleteSubCategory(each.getId());
            }
            contentCategoryMapper.deleteByPrimaryKey(each.getId());
        }
        return 0;
    }
}
