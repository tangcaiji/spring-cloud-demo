package com.tang.producer.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * 测试 test
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${server.port}")
    private String port;

    @GetMapping
    public String test1(String name){

        return "测试 test--模拟成功返回数据：消费者传参 = " + name;
    }

    @GetMapping("/t")
    public String test2(@RequestParam String name){

        return "FeignClient方式 -- 测试 test--模拟成功返回数据：消费者传参 = " + name;
    }

    @PostMapping("/t2")
    public String test3(@RequestParam String a){

        return "测试负载均衡：欢迎访问本接口[端口号" + port + "] == 请求参数：" + a;
    }

    @PostMapping("/t22")
    public String test5(@RequestBody String a){

        return "测试负载均衡：欢迎访问本接口/t22[端口号" + port + "] == @RequestBody->接收请求参数：" + a;
    }


}
