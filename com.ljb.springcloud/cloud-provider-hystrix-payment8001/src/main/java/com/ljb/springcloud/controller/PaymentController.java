package com.ljb.springcloud.controller;

import com.ljb.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;


    @Value("${server.port}")
    public String serverPort;


    @GetMapping("/payment/hystrix/ok/{id}")
    public String paymentInfo_Ok(@PathVariable Integer id) {
        String result = paymentService.paymentInfo_Ok(id);
        log.info("**** result " + result);
        return result;
    }



    @GetMapping("/payment/hystrix/TimeOut/{id}")
    public String TimeOut(@PathVariable Integer id) {
        String result = paymentService.paymentInfo_TimeOut(id);
        log.info("**** result " + result);
        return result;
    }

    /**
     * 服务熔断
     *
     * @param id
     * @return
     */
    @GetMapping("/circuit/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result: " + result);
        return result;
    }
}
