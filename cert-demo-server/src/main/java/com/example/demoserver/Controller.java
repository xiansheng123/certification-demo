package com.example.demoserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("test")
    String test() {
        return "test server http";
    }
}
