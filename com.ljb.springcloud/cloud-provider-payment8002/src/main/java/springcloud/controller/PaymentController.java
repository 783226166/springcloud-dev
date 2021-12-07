package springcloud.controller;

import com.ljb.cpringcloud.entities.CommonResult;
import com.ljb.cpringcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import springcloud.serice.PaymentService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private  String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


    @PostMapping("/payment/create")
    public CommonResult create(Payment payment) {
        int result =paymentService.create(payment);
        log.info("插入结果++"+result);

        if (result>0){
            return new CommonResult(200,"插入成功8002"+serverPort,result);

        }
        return new CommonResult(500,"插入失败",null);
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询结果++"+payment);
        if (payment!=null){
            return new CommonResult(200,"成功"+serverPort,payment);
        }


        return new CommonResult(500,"没有对应记录"+id,null);
    }



    @GetMapping("discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        services.forEach(service->{
            System.out.println("----service"+service);
        });
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId()+"\t" + instance.getHost()+"\t"+ instance.getPort()+"\t"+instance.getUri());;
        }

        return this.discoveryClient;
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


