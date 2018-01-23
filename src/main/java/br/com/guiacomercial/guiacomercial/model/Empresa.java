package br.com.guiacomercial.guiacomercial.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Empresa extends Entidade {

    @NotNull
    private Integer nota;

    @NotNull
    private String nome;

    private String descricao;


}
