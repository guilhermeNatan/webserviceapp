package br.com.guiacomercial.guiacomercial.controller;

import br.com.guiacomercial.guiacomercial.dao.EmpresaRepositorio;
import br.com.guiacomercial.guiacomercial.model.Empresa;
import br.com.guiacomercial.guiacomercial.util.Util;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(Paths.EMPRESAS)
@JsonSerialize
public class EmpresaController {


    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listarEmpresas() {
        List<JSONObject> empresas = new ArrayList<>();

        empresaRepositorio.findAll().forEach(empresa -> {
            JSONObject anuncioJson = new JSONObject();
            anuncioJson.put("id", empresa.getId());
            anuncioJson.put("nome", empresa.getNome());
            anuncioJson.put("nota", empresa.getNota());
            anuncioJson.put("descricao", empresa.getDescricao());
            empresas.add(anuncioJson);
        });
        return new ResponseEntity<>(empresas.toString(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> adicionar(@RequestBody final Empresa empresa) {
        if (empresaRepositorio.save(empresa) != null) {
            return Util.createResponseEntity("Salvou com sucesso",
                    HttpStatus.OK);
        }

        return Util.createResponseEntity("Falha ao salvar anuncio",
                HttpStatus.NOT_FOUND);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> remover(@PathVariable Long id) {
        try {
            empresaRepositorio.delete(id);
            return Util.createResponseEntity("Empresa excluida com sucesso",
                    HttpStatus.OK);
        } catch (ScriptException e) {
            return Util.createResponseEntity("Falha ao excluir empresa",
                    HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> alterarEmpresa(@RequestBody Empresa empresa) {
        try {
            Empresa ep = empresaRepositorio.findOne(empresa.getId());
            ep.setNome(empresa.getNome());
            ep.setNota(empresa.getNota());
            ep.setDescricao(empresa.getDescricao());
            empresaRepositorio.save(ep);
            return Util.createResponseEntity("Empresa atualzada com sucesso",
                    HttpStatus.OK);
        } catch (ScriptException e) {
            return Util.createResponseEntity("Falha ao atualizar empresa",
                    HttpStatus.NOT_FOUND);

        }
    }
}
