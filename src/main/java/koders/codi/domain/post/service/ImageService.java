package koders.codi.domain.post.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor //gcs 업로드
@Slf4j
@Transactional
public class ImageService {

    private final Storage storage;
    private String baseurl =  "https://storage.googleapis.com/codi-bucket/";

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public String uploadImage(MultipartFile file) throws IOException{
        String fileName = UUID.randomUUID().toString();     //gcs에 저장될 파일명 /uuid 사용해서 중복 방지
        String ext = file.getContentType();     //파일 형식 ex) jpg

        //blobinfo : gcs에 저장될 객체의 메타 데이터
        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, fileName)
                        .setContentType(ext)
                        .build(),
                file.getInputStream()
        );

        //db에 저장될 이미지 파일 url
        return baseurl + fileName;
    }
}
