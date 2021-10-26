package com.web.lokacar.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.web.lokacar.exceptions.ServiceExc;
import com.web.lokacar.model.Post;
import com.web.lokacar.model.Usuario;
import com.web.lokacar.repository.PostRepository;
import com.web.lokacar.repository.UsuarioRepository;
import com.web.lokacar.service.ServicePost;
import com.web.lokacar.service.ServiceUsuario;
import com.web.lokacar.util.Util;

import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("usuario")
public class UsuarioController {
   @Autowired
   private ServiceUsuario serviceUsuario;

   @Autowired
   private UsuarioRepository usuarioRepository;

   @Autowired
   private PostRepository postRepository;

   @Autowired
   private ServicePost servicePost;

   private static String path = "src/main/resources/static/banco-img/";


   //-------------login----------------//
   @GetMapping("/login")
   public ModelAndView login(){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/login");
      mv.addObject("usuario", new Usuario());
      return mv ;
   }

   @PostMapping("/login")
   public ModelAndView logar(@Valid Usuario usuario, BindingResult br, HttpSession session) throws NoSuchAlgorithmException, ServiceExc{
      ModelAndView mv = new ModelAndView();
      if(br.hasFieldErrors("email") || br.hasFieldErrors("senha")){
         mv.setViewName("usuario/login");
      }else{
         Usuario userLogin = serviceUsuario.loginUser(usuario.getEmail(),Util.md5(usuario.getSenha()) );
         if(userLogin == null){
            mv.addObject("msg", "Usuário não encontrado. Tente novamente.");
            mv.setViewName("usuario/login");
         }else{
            session.setAttribute("usuarioLogado", userLogin);
            mv.setViewName("redirect:/posts");
         }
      }
      return mv;
  }
    
   //-----------cadastro-------------//

   @GetMapping("/cadastro")
   public ModelAndView cadastro(Usuario usuario){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("usuario/cadastro");
      mv.addObject("usuario", new Usuario());
      return mv ;
   }

   @PostMapping("/cadastro")
   public ModelAndView cadastrar(@Valid Usuario usuario, BindingResult br) throws Exception{
      ModelAndView mv = new ModelAndView();
      if(br.hasErrors()){
         mv.setViewName("usuario/cadastro");
      }else{
         String msg = serviceUsuario.salvarUsuario(usuario);
         if (msg == null){
           mv.setViewName("redirect:/usuario/login");
         }else{
           mv.addObject("msg",msg);
           mv.setViewName("usuario/cadastro");
         }
      }
      
      return mv;
   }

   @PostMapping("/logout")
   public ModelAndView logout(HttpSession session){
      session.invalidate();
      return login();
   }

   //-----------perfil-------------//
   @GetMapping("/perfil/{id}")
   public ModelAndView perfil(@PathVariable("id") Integer id){
      ModelAndView mv = new ModelAndView();
      Usuario usuario = usuarioRepository.getById(id);
      mv.addObject("usuario", usuario);
      mv.setViewName("usuario/perfil");
      return mv ;
   }

   @PostMapping("/alterarUsuario")
   public ModelAndView alterar(@Valid Usuario usuario, BindingResult br) {
      ModelAndView mv = new ModelAndView();
      if(br.hasErrors()){
         mv.setViewName("usuario/perfil");
      }else{
         mv.setViewName("usuario/perfil");
         usuarioRepository.save(usuario);
      }
      return mv;
   }

   @PostMapping("/excluir/{id}")
   public ModelAndView excluir( @PathVariable("id") Integer id, HttpSession session) {
      usuarioRepository.deleteById(id);
      ModelAndView mv = new ModelAndView();
      session.invalidate();
      mv.setViewName("redirect:/usuario/login");
      return mv;
   }

   @GetMapping("/perfilPosts/{id}")
   public ModelAndView perfilPosts(
         @PathVariable("id") Integer id,
         @RequestParam(
            value = "page",
            required = false,
            defaultValue = "0") int page,
         @RequestParam(
            value = "size",
            required = false,
            defaultValue = "4") int size,
         @RequestParam(
            value = "msg",
            required = false) String msg){
      ModelAndView mv = new ModelAndView();
      Usuario usuario = usuarioRepository.getById(id);
   
      Page<Post> posts = servicePost.findByUsuario(usuario, page, size);
      mv.addObject("posts", posts);
      mv.setViewName("usuario/perfilPosts");
      if(msg != null){
         mv.addObject("msg", msg);
      }
      int totalPages = posts.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
            .boxed()
            .collect(Collectors.toList());
         mv.addObject("pageNumbers",pageNumbers);
      }
      return mv;
   }

   @GetMapping("/perfilReservas/{id}")
   public ModelAndView perfilReservas(
         @PathVariable("id") Integer id,
         @RequestParam(
            value = "page",
            required = false,
            defaultValue = "0") int page,
         @RequestParam(
            value = "size",
            required = false,
            defaultValue = "4") int size) {
      ModelAndView mv = new ModelAndView();
      Usuario usuario = usuarioRepository.getById(id);

      Page<Post> posts = servicePost.findByUsuarioReservado(usuario, page, size);
      mv.addObject("posts", posts);
      mv.setViewName("usuario/perfilReservas");

      int totalPages = posts.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
            .boxed()
            .collect(Collectors.toList());
         mv.addObject("pageNumbers",pageNumbers);
      }
      return mv;
   }
   //-------------post---------------//

   @GetMapping("/addPost")
   public ModelAndView addPost(Post post){
      ModelAndView mv = new ModelAndView();
      mv.setViewName("post/addPost");
      mv.addObject("post", post);
      return mv ;
   }

   @PostMapping("/addPost/{id}")
   public ModelAndView post(
         @PathVariable("id") Integer id,
         @Valid Post post,
         BindingResult br,
         @RequestParam(value = "file", required = false) MultipartFile file,
         RedirectAttributes redirectAttributes) throws IOException{
      ModelAndView mv = new ModelAndView();
      if(br.hasErrors()){
         mv.setViewName("post/addPost");
      }else{
         Usuario usuario = usuarioRepository.getById(id);
         post.setUsuario(usuario);
         mv.setViewName("redirect:/usuario/perfilPosts/"+id);
         postRepository.save(post);
         if(!file.isEmpty()){
            if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")){
               byte[] bytes = file.getBytes();
               Path caminho = Paths.get(path+post.getId()+file.getOriginalFilename());
               Files.write(caminho, bytes);
               post.setNomeImagem(post.getId()+file.getOriginalFilename());
               postRepository.save(post);
            }else{
               redirectAttributes.addAttribute("msg","erro-img");
            }
         }
         
      }
      return mv ;
   }
   @GetMapping("/editarPost/{id}")
   public ModelAndView editarPost(@PathVariable("id") Integer id){
      ModelAndView mv = new ModelAndView();
      Post post = postRepository.getById(id);
      mv.addObject("post", post);
      mv.setViewName("post/editarPost");
      return mv ;
   }

   @PostMapping("/editarPost/{id}")
   public ModelAndView alterarPost(
         @PathVariable("id") Integer id,
         @Valid Post post,
         BindingResult br, 
         @RequestParam(value = "file", required = false) MultipartFile file,
         RedirectAttributes redirectAttributes) throws IOException{
      ModelAndView mv = new ModelAndView();
      if(br.hasErrors()){
         return editarPost(post.getId());
      }else{
         Usuario usuario = usuarioRepository.getById(id);
         post.setUsuario(usuario);
         postRepository.save(post);
         mv.setViewName("redirect:/usuario/perfilPosts/{id}");
         
         if(!file.isEmpty()){
            if(file.getContentType().equals("image/png") || file.getContentType().equals("image/jpeg")){
               byte[] bytes = file.getBytes();
               Path caminho = Paths.get(path+post.getId()+file.getOriginalFilename());
               Files.write(caminho, bytes);
               post.setNomeImagem(post.getId()+file.getOriginalFilename());
               postRepository.save(post);
            }else{
               redirectAttributes.addAttribute("msg","erro-img");
            }
            
         }
         
      }
      return mv ;
   }

   @PostMapping("/excluirPost/{id}")
   public ModelAndView excluirPost( @PathVariable("id") Integer id){
      Post post =  postRepository.getById(id);
      Path caminho = Paths.get(path+post.getNomeImagem());
      try{
         Files.delete(caminho);
      }catch(NoSuchFileException ex){
         System.out.println(ex);
      }catch (IOException ex) {
         System.out.println(ex);
      }
      
      Usuario usuario =  post.getUsuario();
      postRepository.deleteById(id);
      ModelAndView mv = new ModelAndView();
      mv.setViewName("redirect:/usuario/perfilPosts/"+usuario.getId());
      return mv;
   }

   @PostMapping("/alugarPost/{postId}/{userId}")
   public ModelAndView alugarPost(
         @PathVariable("postId") Integer postId,
         @PathVariable("userId") Integer userId,
         @RequestParam(
               value = "page",
               required = false,
               defaultValue = "0") int page,
         @RequestParam(
               value = "size",
               required = false,
               defaultValue = "10") int size) {
      ModelAndView mv = new ModelAndView();
      Page<Post> posts = servicePost.findAll(page,size);
      mv.addObject("posts", posts);
      Post post =  postRepository.getById(postId);
      Usuario usuario =  usuarioRepository.getById(userId);
      if(post.getUsuario().getId() == userId){
         mv.addObject("msg","erro");
         mv.setViewName("post/posts");
      }else{
         if(post.getUsuarioReservado() == null){
            post.setUsuarioReservado(usuario);
            postRepository.save(post);
            mv.addObject("msg", "success");
            mv.setViewName("post/posts");
         }else{
            mv.addObject("msg", "erro");
            mv.setViewName("post/posts");
         }
         
      }
      int totalPages = posts.getTotalPages();
      if (totalPages > 0) {
         List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
            .boxed()
            .collect(Collectors.toList());
         mv.addObject("pageNumbers",pageNumbers);
      }
      return mv ;
   }

   @PostMapping("/cancelarReserva/{id}")
   public ModelAndView cancelarReserva(@PathVariable("id") Integer id){
      ModelAndView mv = new ModelAndView();
      Post post = postRepository.getById(id);
      Usuario user = post.getUsuarioReservado();
      Integer userId = user.getId();
      post.setUsuarioReservado(null);
      postRepository.save(post);
      mv.setViewName("redirect:/usuario/perfilReservas/"+userId);
      return mv ;
   }
}
