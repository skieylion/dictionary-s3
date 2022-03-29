package cloud.service.s3.repository;

import cloud.service.s3.model.FileS3;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileRepository {
    void save(String fileId,MultipartFile multipartFile) throws IOException;
    FileS3 get(String fileId) throws IOException;
    void delete();
    List<String> getAll() throws IOException;
}
