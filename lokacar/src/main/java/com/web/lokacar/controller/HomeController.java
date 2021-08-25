package com.web.lokacar.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class HomeController {
   
    @GetMapping("/")
    public ModelAndView index(){
       ModelAndView mv = new ModelAndView();
       mv.setViewName("home/home");
       return mv ;
    }
}
