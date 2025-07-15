package com.tang.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 配置 RestTemplateConfig
 */

@Configuration
public class RestTemplateConfig {

    @Bean(name = "restTemplate")
    /**
     * 不加注解或其他方式进行负载均衡，会报错，RestTemplate将无法解析服务名称
     * -----------------------------------------------------
     * 以下是网友解答：
     * loadbalanced注解的作用就是给你的resttemplate加了一个拦截器，
     * 拿你的hostname换成host:port在发送。
     * 另一个网友：
     * 总结：
     * 1）用户创建RestTemplate
     * 2）添加了ribbon依赖后，会在项目启动的时候自动往RestTemplate中添加LoadBalancerInterceptor拦截器
     * 3）用户根据RestTemplate发起请求时，会将请求转发到LoadBalancerInterceptor去执行，该拦截器会根据指定的负载均衡方式获取该次请求对应的应用服务端IP、port
     * 4）根据获取到的IP、port重新封装请求，发送HTTP请求，返回具体响应
     *
     * -----------------------------------------------------
     * 在restTemplate模板对象上加@LoadBalanced注解后，
     * 为什么加上 @LoadBalanced就不能访问ip加端口呢？
     * 主要因为其使用ribbon组件，当我们应用依赖了eureka-client的时候，eureka-client依赖了ribbon，
     * 虽然看是ribbon没有存在感，但是ribbon默默的发挥着自己的负载均衡能力。在加了注解 @LoadBalanced 之后，
     * 我们的restTemplate 会走这个类RibbonLoadBalancerClient，serverid必须是我们访问的服务名称 ，当我们
     * 直接输入ip的时候获取的server是null，就会抛出异常。而这个服务名称是需要我们在Eureka中配置的，如果没有配置
     * 直接访问则会报错。
     *
     * 这里如果配置了这个注解，访问路径是IP会报错，解析不到服务：
     * 因为ribbon的作用是负载均衡，那么你直接使用ip地址，那么就无法起到负载均衡的作用，因为每次都是调用同一个服务，
     * 当你使用的是服务名称的时候，他会根据自己的算法去选择具有该服务名称的服务。
     * 结论：因为我的IP没有在Eureka中经行配置。
     * 为什么使用另外新的new RestTemplate则可以正常访问？
     * 因为添加了ribbon依赖后，会在项目启动的时候自动往RestTemplate中添加LoadBalancerInterceptor拦截器，
     * 直接new的对象没有加@LoadBalanced，通过Debug工具发现在访问时没有被该拦截器拦截。所以就能正常访问。
     *
     * -----------------------------------------------------
     * 使用的是Spring Cloud 2020.0.x版本或更高版本，建议使用Spring Cloud LoadBalancer替代Ribbon：
     * 包：spring-cloud-starter-loadbalancer
     * @Bean
     * public RestTemplate restTemplate(RestTemplateBuilder builder) {
     *    return builder.build(); // 自动使用LoadBalancerClient进行负载均衡
     * }
     */
    @LoadBalanced
    @Primary
    public RestTemplate restTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory facotry = new SimpleClientHttpRequestFactory();
        facotry.setReadTimeout(120000);
        facotry.setConnectTimeout(30000);
        facotry.setBufferRequestBody(false);
        return facotry;
    }

    //配置第二个
    @Bean(name = "restTemplateCustom")
    public RestTemplate restTemplateCustom(SimpleClientHttpRequestFactory simpleClientHttpRequestFactory){

        RestTemplate restTemplate = new RestTemplate();
        //restTemplate.setErrorHandler(new RestTemplateErrorHandler()); 没有导入这个包，先不用
        return restTemplate;
    }

}
