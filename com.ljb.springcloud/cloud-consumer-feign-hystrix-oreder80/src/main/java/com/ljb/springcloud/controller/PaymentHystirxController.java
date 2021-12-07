package com.ljb.springcloud.controller;

import com.ljb.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "defaultProperties")
public class PaymentHystirxController {
    @Resource
    private PaymentHystrixService service;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable Integer id) {
        return service.paymentInfo_Ok(id);
    }


    //    @HystrixCommand(fallbackMethod = "paymentInfoTimeOutHandler", commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")
//    })
    @HystrixCommand
    @GetMapping("/consumer/payment/hystrix/TimeOut/{id}")
    public String TimeOut(@PathVariable Integer id) {
        int n = 10 / 0;
        return service.TimeOut(id);
    }


    public String paymentInfoTimeOutHandler(Integer id) {
        return "线程池:80  " + Thread.currentThread().getName() + "  paymentInfoTimeOutHandler8001系统繁忙或者运行报错，请稍后再试,id:  " + id + "\t"
                + "o(╥﹏╥)o";
    }
    public String defaultProperties() {
        return "线程池:80  " + Thread.currentThread().getName() + "  defaultProperties8001系统繁忙或者运行报错，请稍后再试,id:  " + "\t"
                + "o(╥﹏╥)o";
    }


}
