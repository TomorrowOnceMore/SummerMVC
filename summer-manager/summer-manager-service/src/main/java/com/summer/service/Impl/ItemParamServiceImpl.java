package com.summer.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.summer.common.pojo.EasyUIDataGridResult;
import com.summer.common.pojo.SummerResult;
import com.summer.mapper.TbItemParamMapper;
import com.summer.pojo.TbItemParam;
import com.summer.pojo.TbItemParamExample;
import com.summer.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by toy on 6/15/16.
 */
@Service
public class ItemParamServiceImpl  implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public SummerResult getItemParamByCid(Long cid) {
        //
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);

        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);

        if (list != null && list.size() > 0) {
            TbItemParam itemParam = list.get(0);
            return SummerResult.ok(itemParam);
        }
        return SummerResult.ok();
    }


    @Override
    public EasyUIDataGridResult getItemParamList(int page, int rows) {
        PageHelper.startPage(page, rows);
        TbItemParamExample example = new TbItemParamExample();
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);

        EasyUIDataGridResult result = new EasyUIDataGridResult();
        result.setTotal(pageInfo.getTotal());
        result.setRows(list);
        return result;
    }

    @Override
    public SummerResult insertItemParam(Long cid, String paramDate) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramDate);
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        itemParamMapper.insert(itemParam);
        return SummerResult.ok();
    }
}
