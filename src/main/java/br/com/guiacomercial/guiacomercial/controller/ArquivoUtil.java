package br.com.guiacomercial.guiacomercial.controller;

import br.com.guiacomercial.guiacomercial.dao.ArquivoRepositorio;
import br.com.guiacomercial.guiacomercial.model.Arquivo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ArquivoUtil {

    @Autowired
    private ArquivoRepositorio arquivoRepositorio;

    public Arquivo uploadFile(MultipartFile uploadfile) {
        try {
            return saveUploadedFiles(uploadfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //save file
    private Arquivo saveUploadedFiles(MultipartFile file) throws IOException {
            Arquivo arquivo = new Arquivo();
            arquivo.setArquivo(file.getBytes());
            arquivo.setNome(file.getOriginalFilename());
            arquivo.setTamanhoKB((int)file.getSize()/1024);
            return  arquivoRepositorio.save(arquivo);
}
}
