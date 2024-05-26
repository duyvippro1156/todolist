
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/") 
public class demoController {
    
    @GetMapping("index")
    public String index(Model model) {
        model.addAttribute("message", "hello");
        return "abc";
    }
    
}
