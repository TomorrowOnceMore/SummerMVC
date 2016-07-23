package com.summer.sso.service;

import com.summer.common.pojo.SummerResult;
import com.summer.pojo.TbUser;

/**
 * Created by toy on 7/17/16.
 */
public interface RegisterService {
    SummerResult checkData(String param, int type);
    SummerResult register(TbUser user);
}
