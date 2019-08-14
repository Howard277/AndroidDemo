package com.example.androiddemo.utils;

/**
 * http请求结果处理器
 */
public interface HttpResponseHandler {
    /**
     * 处理Http请求结果
     *
     * @param tag          请求标志，用来区分不同请求。
     * @param responseBody 请求处理结果内容
     */
    void DealwithHttpResponse(String tag, String responseBody);
}
