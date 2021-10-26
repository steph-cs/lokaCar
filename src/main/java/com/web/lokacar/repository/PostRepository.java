package com.web.lokacar.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.web.lokacar.enums.Localizacao;
import com.web.lokacar.enums.Tipo;
import com.web.lokacar.model.Post;
import com.web.lokacar.model.Usuario;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUsuario(Usuario usuario);
    List<Post> findByUsuarioReservado(Usuario usuarioReservado);

    Page<Post> findByLocalizacaoAndTipo(
            Localizacao localizacao,
            Tipo tipo,
            Pageable pageable);

    Page<Post> findAll( 
            Pageable pageable);

    Page<Post> findByUsuarioReservado( 
            Usuario usuario,
            Pageable pageable);

    Page<Post> findByUsuario(
            Usuario usuario, 
            Pageable pageable);
}