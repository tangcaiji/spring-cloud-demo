package com.tang.consumer.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试 test
 */
@RestController
@RequestMapping("testc")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestService testService;

    //调用方式一: 使用RestTemplate调用注册子模块接口方法
    @GetMapping("/{name}")
    public String test(@PathVariable("name") String name){

        String tem = restTemplate.getForObject("http://EUREKA-PRODUCER/test?name=" + name
                , String.class);

        return tem;
    }

    //调用方式二: 使用@FeignClient调用注册子模块接口方法
    @GetMapping("/2/{a}")
    public String test2(@PathVariable("a") String a){

        return testService.test(a);
    }

    //调用方式二: 使用@FeignClient调用注册子模块接口方法
    @GetMapping("/3/{a}")
    public String test3(@PathVariable("a") String a){

        String b = testService.test2(a);
        //b = b + "|<-->|" + testService.test22(a);
        return b;
    }


}
