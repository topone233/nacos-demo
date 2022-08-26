package com.example.feignclient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author liyuan
 * @date 2022/08/25
 */
@org.springframework.cloud.openfeign.FeignClient(name = "feign-server")
public interface FeignClient {

    /**
     * 通过feign调用server的方法
     */
    @GetMapping("server/getName/{name}")
    public String test(@PathVariable("name") String name);
}
