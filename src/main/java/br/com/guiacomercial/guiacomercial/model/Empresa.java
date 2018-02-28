package br.com.guiacomercial.guiacomercial.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Empresa extends Entidade {

    @NotNull
    private Integer nota;

    @NotNull
    @Length(max = 255)
    private String nome;

    @Length(max = 1000)
    private String descricao;

    @OneToOne
    Arquivo imagem;
}
