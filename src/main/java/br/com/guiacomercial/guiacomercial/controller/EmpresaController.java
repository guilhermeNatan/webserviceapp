package br.com.guiacomercial.guiacomercial.controller;

import br.com.guiacomercial.guiacomercial.dao.EmpresaRepositorio;
import br.com.guiacomercial.guiacomercial.model.Empresa;
import br.com.guiacomercial.guiacomercial.util.Util;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Serviço para acesso as empresas
 */
@RestController
@RequestMapping(Paths.EMPRESAS)
@JsonSerialize
public class EmpresaController {

    @Autowired
    private EmpresaRepositorio empresaRepositorio;

    /**
     * @return Objeto json com todas as empresas cadastradas .
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listarEmpresas() {
        List<JSONObject> empresas = new ArrayList<>();

        empresaRepositorio.findAll().forEach(empresa -> {
            JSONObject empresaJson = new JSONObject();
            empresaJson.put("id", empresa.getId());
            empresaJson.put("nome", empresa.getNome());
            empresaJson.put("nota", empresa.getNota());
            empresaJson.put("descricao", empresa.getDescricao());
            empresas.add(empresaJson);
        });
        return new ResponseEntity<>(empresas.toString(), HttpStatus.OK);
    }

    /**
     * @param id . id da empresa
     * @return Objeto json com uma empresa especifica
     */
    @RequestMapping(value = "/emp/{id}" , method = RequestMethod.GET)
    public ResponseEntity<Object> obterEmpresa(@PathVariable  Long id) {
        Empresa empresa = empresaRepositorio.findOne(id);
        JSONObject empresaJson = new JSONObject();
        empresaJson.put("id", empresa.getId());
        empresaJson.put("nome", empresa.getNome());
        empresaJson.put("nota", empresa.getNota());
        empresaJson.put("descricao", empresa.getDescricao());

        return new ResponseEntity<>(empresaJson.toString(), HttpStatus.OK);
    }

    /**
     * @param empresa
     * @return Mensagem informado se empresa foi salva ou não com sucesso no banco de dados.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> adicionar(@RequestBody final Empresa empresa) {
        if (empresaRepositorio.save(empresa) != null) {
            return Util.createResponseEntity("Salvou com sucesso",
                    HttpStatus.OK);
        }

        return Util.createResponseEntity("Falha ao salvar empresa",
                HttpStatus.NOT_FOUND);
    }


    /**
     * Exclui uma empresa
     * @param id id da empresa a ser excluida
     * @return  Mensagem informado se empresa foi excluida ou não com sucesso no banco de dados.
     */
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

    /**
     * Realiza a operação de alteração de uma empresa no banco de dados .
     * @param empresa
     * @return Mensagem informado se empresa foi alterado ou não com sucesso no banco de dados.
     */
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
