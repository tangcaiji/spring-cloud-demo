package com.tang.consumer.test;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    /**
     * 本接口为调用服务的接口
     * hystrixReturn方法是自定义的一个熔断器处理返回的方法，HystrixCommand就是熔断器处理注解，
     * fallbackMethod即是出现错误调用哪个自定义方法进行处理。里面有很多处理方法，可以去慢慢看
     *
     * 熔断器的默认配置是1000ms，若请求时间超过1000ms，熔断器就会认为这是一个出错，就会执行配置的
     */
    @GetMapping("/h")
    @HystrixCommand(fallbackMethod = "hystrixReturn")
    public String test5(){
        String name = Thread.currentThread().getName();
        System.out.println("当前线程：" + name);
        String result = testService.hy();
        System.out.println("结束");
        return result;
    }

    //当访问出现错误时，会返回自定义的方法返回的结果。
    public String hystrixReturn(){
        return "服务爆满，请等待服务恢复正常！";
    }


}
