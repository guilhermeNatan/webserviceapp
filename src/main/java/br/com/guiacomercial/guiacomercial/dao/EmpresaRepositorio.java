package br.com.guiacomercial.guiacomercial.dao;

import br.com.guiacomercial.guiacomercial.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmpresaRepositorio extends CrudRepository<Empresa, Long> {
}