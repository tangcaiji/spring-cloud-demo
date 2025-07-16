package com.tang.consumer.test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

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
    //接口访问地址【负载均衡的策略】
    @GetMapping("test/t")
    String test(@RequestParam String name);
    //测试负载均衡：feign默认是轮询的策略，演示的也是轮询，就是请求服务实例挨个一个一个来。
    @PostMapping("/test/t2")
    String test2(@RequestParam String a);

    @PostMapping("/test/t22")
    String test22(@RequestBody String a);

    @GetMapping("/test/hy")
    String hy();

}
