package br.uniesp.fuji.cloudaws.service;


import br.uniesp.fuji.cloudaws.model.MetaDados;
import br.uniesp.fuji.cloudaws.repository.MetaDadosRepository;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class MetadataServiceImpl implements MetadataService {

    @Autowired
    private AwsS3Service s3Service;

    @Autowired
    private MetaDadosRepository metaDadosRepository;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    @Override
    public void upload(MultipartFile file) throws IOException {

        if (file.isEmpty())
            throw new IllegalStateException("Cannot upload empty file");

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));

        String path = String.format("%s/%s", bucketName, UUID.randomUUID());
        String fileName = String.format("%s", file.getOriginalFilename());

        // Este pomto faz upload doa rquivo
        PutObjectResult putObjectResult = s3Service.upload(
                path, fileName, Optional.of(metadata), file.getInputStream());

        // Saving metadata to db
        metaDadosRepository.save(new MetaDados(fileName, path, putObjectResult.getMetadata().getVersionId()));

    }

    @Override
    public S3Object download(int id) {
        MetaDados fileMeta = metaDadosRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return s3Service.download(fileMeta.getFilePath(),fileMeta.getFileName());
    }

    @Override
    public List<MetaDados> list() {
        List<MetaDados> metas = new ArrayList<>();
        metaDadosRepository.findAll().forEach(metas::add);
        return metas;
    }
}