package com.summer.sso.service;

import com.summer.common.pojo.SummerResult;

/**
 * Created by toy on 7/18/16.
 */
public interface LogoutService {
    SummerResult logout(String token);
}
