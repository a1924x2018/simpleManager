package com.example.server.manager.service.upload.impl;

import com.example.server.manager.rpc.qiniu.QiniuImageUploadRpc;
import com.example.server.manager.service.base.BaseService;
import com.example.server.manager.service.upload.ImageUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
@Slf4j
public class ImageUploadServiceImpl extends BaseService implements ImageUploadService {

    @Autowired
    private QiniuImageUploadRpc qiniuImageUploadRpc;

    @Override
    public String uploadToQiniu(MultipartFile uploadFile) {
        return qiniuImageUploadRpc.uploadImage(uploadFile);
    }
}
