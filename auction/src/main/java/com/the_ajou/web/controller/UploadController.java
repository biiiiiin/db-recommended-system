package com.the_ajou.web.controller;

import com.the_ajou.service.UploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UploadController {
    private final UploadService s3Upload;

    @PostMapping("/api/upload")
    public String uploadFile(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        return s3Upload.upload(multipartFile);
    }
}
