package com.example.server.manager.rpc.qiniu;

import org.springframework.web.multipart.MultipartFile;

public interface QiniuImageUploadRpc {
    /**
     * 上传图片到七牛云
     */
    String uploadImage(MultipartFile uploadFile);
}
