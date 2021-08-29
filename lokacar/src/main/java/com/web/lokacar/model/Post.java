package com.web.lokacar.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.web.lokacar.enums.Localizacao;
import com.web.lokacar.enums.Tipo;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String descricao;

    @NotBlank(message = "Campo obrigatório!")
    private String modelo;

    @NotNull(message = "Campo obrigatório!")
    @Range(min=50,max=500,message= "Preco deve ser entre 50 e 500 reais!")
    private Integer preco;

    @Enumerated(EnumType.STRING)
    private Localizacao localizacao;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    @OneToOne
    private Usuario usuario;

    @OneToOne
    private Usuario usuarioReservado;
}
