package com.mock.http;


import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/*
public class HelloWireMockTest {
    private static final int PORT = 18888;
    */
/**
     * 使用给定的端口号生成WireMockRule实例.
     * 这里设置之后，启动测试时 WireMock 会使用内嵌的 Jetty 启动一个 Web 服务器并监听指定的端口
     *//*

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(options().port(PORT));

    @Test
    public void helloTest() {
        String uri = "/say/hello";
        String body = "Hello World!";
        // 给uri打桩，这个语句表示，拦截 uri 为 /say/hello 的请求回复 "Hello World!"
        wireMockRule.stubFor(get(urlEqualTo(uri))
                .willReturn(aResponse().withBody(body)));
        // 这里我们可以用任何方式发起HTTP的GET请求
        String result = HttpUtil.get("http://localhost:" + PORT + uri);
        System.out.println(result);
        // 断言我们发出的请求返回了我们期望的结果
        assertEquals(result, body);
    }
}*/
