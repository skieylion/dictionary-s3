package cloud.service.s3.repository;

import cloud.service.s3.model.FileS3;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FileRepositoryImpl implements FileRepository {

    private final AmazonS3 amazonS3;
    private final String bucketName;

    public FileRepositoryImpl(AmazonS3 amazonS3, @Value("${app.s3.bucketName}") String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }


    @Override
    public void save(String fileId, MultipartFile multipartFile) throws IOException {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(multipartFile.getContentType());
        data.setContentLength(multipartFile.getSize());
        data.addUserMetadata("name",multipartFile.getOriginalFilename());
        amazonS3.putObject(bucketName, fileId, multipartFile.getInputStream(), data);
    }

    @Override
    public FileS3 get(String fileId) {
        S3Object s3Object = amazonS3.getObject(bucketName, fileId);
        ObjectMetadata data = amazonS3.getObjectMetadata(bucketName, fileId);
        String fileName = data.getUserMetadata().get("name");
        return new FileS3(fileName, s3Object.getObjectContent());
    }

    @Override
    public void delete() {

    }

    @Override
    public List<String> getAll() throws IOException {
        ObjectListing listing = amazonS3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = listing.getObjectSummaries();

        List<String> files = new ArrayList<>();
        summaries.forEach(s3ObjectSummary -> {
            files.add(s3ObjectSummary.getKey());
        });
        return files;
    }
}
