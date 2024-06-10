package com.test.bootoauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {


    @GetMapping(value="/my")
    public String my() {
        return "my";
    }
}

