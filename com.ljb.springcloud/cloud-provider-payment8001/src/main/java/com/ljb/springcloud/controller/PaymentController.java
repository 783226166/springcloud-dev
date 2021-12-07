package com.ljb.springcloud.controller;

import com.ljb.cpringcloud.entities.CommonResult;
import com.ljb.cpringcloud.entities.Payment;
import com.ljb.springcloud.serice.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(Payment payment) {
        int result = paymentService.create(payment);
        log.info("插入结果++" + result);

        if (result > 0) {
            return new CommonResult(200, "插入成功" + serverPort, result);

        }
        return new CommonResult(500, "插入失败", null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果++" + payment);
        if (payment != null) {
            return new CommonResult(200, "成功8001" + serverPort, payment);
        }


        return new CommonResult(500, "没有对应记录" + id, null);
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB() {
        return serverPort;
    }

    @GetMapping("payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        }catch (InterruptedException e){

        }

        return serverPort;
    }

}


