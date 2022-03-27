package cloud.service.s3.controller;

import cloud.service.s3.model.FileS3;
import cloud.service.s3.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

//@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
public class FileCtrl {

    private final FileRepository fileRepository;

    @PostMapping(value = "/File", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void upload(@RequestParam("fileId") String fileId, @RequestPart("file") MultipartFile multipartFile) throws IOException {
        fileRepository.save(fileId, multipartFile);
    }

    @GetMapping("/File/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) throws IOException {
        FileS3 fileS3 = fileRepository.get(fileId);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileS3.getFileName() + "\"")
                .body(fileS3.getResource());
    }

    @GetMapping("/File")
    public List<String> showAllFiles() throws IOException {
        return fileRepository.getAll();
    }
}
