package com.example.feignserver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyuan
 * @date 2022/08/25
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("server")
public class ServerController {

    @GetMapping("getName/{name}")
    public String getName(@PathVariable("name") String name) {
        return "Hello " + name;
    }
}
