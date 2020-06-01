package com.lidadaibiao.nacos.consumer.controller;


import com.lidadaibiao.microservice.service1.api.Service1Api;
import com.lidadaibiao.microservice.service2.api.Service2Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Lidadaibiao
 * @date 2020/5/31 - 16:57
 */
@RestController
public class RestConsumerController {


    //要进行远程，需要知识提供方的ip和端口
    @Value("${provider.address}")
    private String provider;

    @GetMapping(value = "/service")
    public String service(){
        //远程调用
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("http://" + provider + "/service", String.class);
        return "consumer invode|"+result;
    }


    //指定服务名
    String serviceId = "nacos-restful-provider";

    //通过负载均衡发现地址，流程是从服务发现中心拿nacos-restful-provider服务的列表，通过负载均衡算法获取一个地址
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @GetMapping(value = "/service1")
    public String service1(){
        //远程调用
        RestTemplate restTemplate = new RestTemplate();
        //发现一个地址
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        //获取一个http://开头的地址，包括ip和端口
        URI uri = serviceInstance.getUri();
        String result = restTemplate.getForObject(uri + "/service", String.class);
        return "consumer invode|"+result;
    }

    @org.apache.dubbo.config.annotation.Reference
    Service2Api service2Api;

    @GetMapping(value = "/service2")
    public String service2(){
        //远程调用service2
        String providerResult = service2Api.dubboService2();
        return "consumer dubbo invoke|"+providerResult;
    }

    @org.apache.dubbo.config.annotation.Reference
    Service1Api service1Api;

    @GetMapping(value = "/service3")
    public String service3(){
        //远程调用service1
        String s = service1Api.dubboService1();
        return "consumer dubbo invoke|"+s;
    }

/*    //从配置中心取出配置文件
    @Value("${common.name}")
    private String common_name;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;
    @GetMapping(value = "/config")
    public String config(){
      String name =   configurableApplicationContext.getEnvironment().getProperty("common.name");
      return s;
}*/
    //从配置中心取出配置文件  扩展配置
    @Value("${common.name}")
    private String common_name;
  /*  @Value("${common.addr}")
    private String common_addr;*/
    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;
    @GetMapping(value = "/config")
    public String config(){
        String name =   configurableApplicationContext.getEnvironment().getProperty("common.name");
       String addr =   configurableApplicationContext.getEnvironment().getProperty("common.addr");
        return name+" "+addr;
    }
}
