package com.web.lokacar.service;

import com.web.lokacar.enums.Localizacao;
import com.web.lokacar.enums.Tipo;
import com.web.lokacar.model.Post;
import com.web.lokacar.model.Usuario;
import com.web.lokacar.repository.PostRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ServicePost {
    
    @Autowired
    PostRepository postRepository;

    public Page<Post> findByLocalizacaoAndTipo(
            Localizacao localizacao,
            Tipo tipo,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "modelo");

        return postRepository.findByLocalizacaoAndTipo(
                localizacao,
                tipo,
                pageRequest);
    }

    public Page<Post> findAll(
           int page,
           int size) {
        
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "modelo");
        return postRepository.findAll(pageRequest);
    }

    public Page<Post> findByUsuario(
            Usuario usuario,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "modelo");

        return postRepository.findByUsuario(
                usuario,
                pageRequest);
    }

    public Page<Post> findByUsuarioReservado(
            Usuario usuario,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "modelo");

        return postRepository.findByUsuarioReservado(
                usuario,
                pageRequest);
    }
}
