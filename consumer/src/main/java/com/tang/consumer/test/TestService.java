package com.tang.consumer.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//注册子模块名称
@FeignClient(value = "EUREKA-PRODUCER")
public interface TestService {

    /**
     * Feign发送的是Get请求，到了提供者这边却变成了Post
     * 因为Feign默认使用的连接工具实现类，所以里面发现只要你有body体对象，就会强制的把GET请求转换成POST请求
     * 解决办法:
     * 1）在Feign接口pom.xml中加入依赖{httpclient ; feign-httpclient}
     * 2）在服务调用方加入: feign-httpclient-enabled: true
     * 或者修改服务方访问方式：post
     */
    //接口访问地址
    @GetMapping("test/t")
    String test(String name);

}
