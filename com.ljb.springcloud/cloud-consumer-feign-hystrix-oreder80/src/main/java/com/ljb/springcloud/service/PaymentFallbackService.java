package com.ljb.springcloud.service;

import org.springframework.stereotype.Component;

@Component

public class PaymentFallbackService implements PaymentHystrixService{
    @Override
    public String paymentInfo_Ok(Integer id) {
        return "-------------PaymentFallbackService paymentInfo_Ok fall back /(ㄒoㄒ)/~~";
    }

    @Override
    public String TimeOut(Integer id) {
        return "-------------PaymentFallbackService  TimeOut fall back /(ㄒoㄒ)/~~";
    }
}
