package com.cruway.springmall;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("index")
    public String hello(Model model) {
        model.addAttribute("message", "hello world");
        return "index";
    }
}