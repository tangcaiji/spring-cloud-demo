package com.tang.producer.test;

import org.springframework.web.bind.annotation.*;

/**
 * 测试 test
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test1(String name){

        return "测试 test--模拟成功返回数据：消费者传参 = " + name;
    }

    @PostMapping("/t")
    public String test2(@RequestBody String name){

        return "FeignClient方式 -- 测试 test--模拟成功返回数据：消费者传参 = " + name;
    }


}
