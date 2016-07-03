package com.summer.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toy on 7/3/16.
 */
public class HttpClientTest {

    @Test
    public void testHttpGet() throws Exception {
        // create httpclient obj
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // request get
        HttpGet get = new HttpGet("http://www.sougou.com");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity);
        System.out.println(html);
        response.close();
        httpClient.close();

    }

    @Test
    public void testHttpPost() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8082/posttest.html");
        List<NameValuePair> formList = new ArrayList<>();
        formList.add(new BasicNameValuePair("name", "而且"));
        formList.add(new BasicNameValuePair("pass", "123421"));
        StringEntity entity = new UrlEncodedFormEntity(formList);
        post.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println(result);
    }
}
