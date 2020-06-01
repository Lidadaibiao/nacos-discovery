package com.lidadaibiao.microservice.service2.service;

import com.lidadaibiao.microservice.service2.api.Service2Api;


/**
 * @author Lidadaibiao
 * @date 2020/6/1 - 10:43
 */
@org.apache.dubbo.config.annotation.Service
public class Service2ApiImpl implements Service2Api {
    public String dubboService2() {
        return "dubboService2";
    }
}
