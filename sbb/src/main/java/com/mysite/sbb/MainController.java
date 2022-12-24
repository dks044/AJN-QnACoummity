package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/ajn")
    @ResponseBody
    public String index() {
    	return "test ajn에 오신것을 환영합니다.";
    }
    
    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }
}