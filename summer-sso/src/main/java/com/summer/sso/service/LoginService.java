package com.summer.sso.service;

import com.summer.common.pojo.SummerResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by toy on 7/17/16.
 */
public interface LoginService {
    SummerResult login(String username, String password, HttpServletRequest request, HttpServletResponse response);
    SummerResult getUserByToken(String token);
}
