package com.web.lokacar.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import com.web.lokacar.enums.Localizacao;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min = 3,max = 20)
    @NotBlank(message = "Campo obrigatório!")
    private String nome;

    @Size(min = 3,max = 20)
    @NotBlank(message = "Campo obrigatório!")
    private String sobrenome;

    @Enumerated(EnumType.STRING)
    private Localizacao localizacao;

    @Email(message = "Email invalido!")
    @NotBlank(message = "Campo obrigatório!")
    private String email;

    @NotBlank(message = "Campo obrigatório!")
    private String senha;

    @OneToMany
    private List<Post> posts;

    @OneToMany
    private List<Post> reservas;
}
