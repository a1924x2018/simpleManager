package com.example.server.manager.rpc.qiniu.impl;

import com.alibaba.fastjson.JSON;
import com.example.server.manager.rpc.base.BaseRpc;
import com.example.server.manager.rpc.qiniu.QiniuImageUploadRpc;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
@Slf4j
public class QiniuImageUploadRpcImpl extends BaseRpc implements QiniuImageUploadRpc {

    @Value("${qiniu.imageUpload.accessKey}")
    private String accessKey;
    @Value("${qiniu.imageUpload.secretKey}")
    private String secretKey;
    @Value("${qiniu.imageUpload.bucket}")
    private String bucket;

    @Override
    public String uploadImage(MultipartFile uploadFile) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        UploadManager uploadManager = new UploadManager(cfg);

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = uploadFile.getBytes();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return putRet.key;

        } catch (Exception e) {
            log.error("QiniuImageUploadRpcImpl.uploadImage error", e);
        }
        return null;
    }
}
