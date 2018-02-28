package br.com.guiacomercial.guiacomercial.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Arquivo extends Entidade {

    private byte[] arquivo;
    private int tamanhoKB;
    private String nome;

}
