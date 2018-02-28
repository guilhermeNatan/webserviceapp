package br.com.guiacomercial.guiacomercial.controller;

import br.com.guiacomercial.guiacomercial.dao.ArquivoRepositorio;
import br.com.guiacomercial.guiacomercial.model.Arquivo;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@RestController
@JsonSerialize
public class ArquivoUploadController {

    private static String PASTA_UPLOAD = "C://Users//User//IdeaProjects//guiacomercial//arquivos//";
    private final Logger logger = LoggerFactory.getLogger(ArquivoUploadController.class);

    @Autowired
    private ArquivoRepositorio arquivoRepositorio;

    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        logger.debug("Single file upload!");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    //save file
    private void saveUploadedFiles(List<MultipartFile> files) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(PASTA_UPLOAD + file.getOriginalFilename());
            Files.write(path, bytes);
            Arquivo arquivo = new Arquivo();
            arquivo.setArquivo(file.getBytes());
            arquivo.setNome(file.getOriginalFilename());
            arquivo.setTamanhoKB((int)file.getSize()/1024);
            arquivoRepositorio.save(arquivo);
        }

    }
}
