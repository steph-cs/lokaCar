package com.web.lokacar.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErroController implements ErrorController {

    @GetMapping("/error")
    public ModelAndView erro(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home/erro");
        return mv ;
     }
}
