package com.example.server.manager.service.upload;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String uploadToQiniu(MultipartFile uploadFile);
}
