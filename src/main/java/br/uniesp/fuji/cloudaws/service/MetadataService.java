package br.uniesp.fuji.cloudaws.service;

import br.uniesp.fuji.cloudaws.model.MetaDados;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MetadataService {
    public void upload(MultipartFile file) throws IOException;
    public S3Object download(int id);
    public List<MetaDados> list();
}