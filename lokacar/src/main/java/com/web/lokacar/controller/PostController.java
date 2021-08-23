package com.web.lokacar.controller;

import javax.validation.Valid;

import com.web.lokacar.model.Post;
import com.web.lokacar.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class PostController {
     
    @Autowired
    private PostRepository postRepository;


    @GetMapping("/posts")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post/posts");
        return mv ;
    }
      
    @GetMapping("/addPost")
    public ModelAndView addPost (Post post) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("post/addPost");
        mv.addObject("post", post);
        return mv;
    }
    
    @PostMapping("/addPost")
    public ModelAndView post(@Valid Post post, BindingResult br){
        ModelAndView mv = new ModelAndView();
        if(br.hasErrors()){
            mv.setViewName("post/addPost");
        }else{
            postRepository.save(post);
            mv.setViewName("redirect:/posts");
        }
        return mv;
    }
}
    