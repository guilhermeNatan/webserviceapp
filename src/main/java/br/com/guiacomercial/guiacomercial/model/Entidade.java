package br.com.guiacomercial.guiacomercial.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Created by guilherme on 02/09/17.
 */
@MappedSuperclass
@Setter
@Getter
public class Entidade {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Long versao;
}
