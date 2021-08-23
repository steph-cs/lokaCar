package com.web.lokacar.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@RequestMapping("usuario")
public class UsuarioController {
   //-------------login----------------//
   @GetMapping("/login")
   public ModelAndView login(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/login");
      return mv ;
   }

   @PostMapping("/login")
   public ModelAndView logar(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("home/home");
      return mv ;
   }
    
   //-----------cadastro-------------//

   @GetMapping("/cadastro")
   public ModelAndView cadastro(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/cadastro");
      return mv ;
   }

   @PostMapping("/cadastro")
   public ModelAndView cadastrar(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("home/home");
      return mv ;
   }

   //-----------perfil-------------//
   @GetMapping("/perfil")
   public ModelAndView perfil(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/perfil");
      return mv ;
   }

   @GetMapping("/perfilPosts")
    public ModelAndView perfilPosts(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/perfilPosts");
      return mv ;
   }

   @GetMapping("/perfilReservas")
   public ModelAndView perfilReservas(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/perfilReservas");
      return mv ;
   }
   //--------------------------------//

   @PostMapping("/excluirPost")
   public ModelAndView excluirPost(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("");
      return mv ;
   }

   @PostMapping("/cancelarReserva")
   public ModelAndView cancelarReserva(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("");
      return mv ;
   }
}
