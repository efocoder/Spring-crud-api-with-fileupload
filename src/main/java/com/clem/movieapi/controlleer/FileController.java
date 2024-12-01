package com.clem.movieapi.controlleer;

import com.clem.movieapi.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    @Value("${project.poster}")
    private String path;

    @Autowired
    public FileController(FileService theFileService) {
        fileService = theFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestPart MultipartFile file) throws IOException {
        String uploadedFileName = fileService.uploadFile(path, file);
        return ResponseEntity.ok("File uploaded successfully " + uploadedFileName);
    }

    @GetMapping("/{fileName}")
    public void serveFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        var resourceFile = fileService.getResourceFile(path, fileName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}