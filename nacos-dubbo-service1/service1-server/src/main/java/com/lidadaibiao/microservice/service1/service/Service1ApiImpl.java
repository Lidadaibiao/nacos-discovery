package com.lidadaibiao.microservice.service1.service;

import com.lidadaibiao.microservice.service1.api.Service1Api;
import com.lidadaibiao.microservice.service2.api.Service2Api;

/**
 * @author Lidadaibiao
 * @date 2020/6/1 - 12:02
 */
@org.apache.dubbo.config.annotation.Service
public class Service1ApiImpl implements Service1Api {


    @org.apache.dubbo.config.annotation.Reference
    Service2Api service2Api;


    public String dubboService1() {

        String s = service2Api.dubboService2();

        return "dubboService1||"+s;
    }
}
