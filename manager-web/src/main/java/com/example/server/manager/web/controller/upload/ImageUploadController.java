package com.example.server.manager.web.controller.upload;

import com.example.server.manager.common.enums.ResultEnum;
import com.example.server.manager.common.protocol.ActionResult;
import com.example.server.manager.common.util.ResultTool;
import com.example.server.manager.service.upload.ImageUploadService;
import com.example.server.manager.web.controller.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class ImageUploadController extends BaseController {

    @Value("${qiniu.imageUpload.domain}")
    private String domain;
    @Autowired
    private ImageUploadService imageUploadService;

    @PostMapping("/json/image/upload")
    @ResponseBody
    public Object uploadImage(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(value = "file") MultipartFile uploadFile) {
        try {
            prepare(request, response);
            if (currentUser == null) {
                return ResultTool.error(ResultEnum.ERROR, "currentUser is null.");
            }
            ActionResult result = new ActionResult();
            String key = imageUploadService.uploadToQiniu(uploadFile);
            if (StringUtils.isBlank(key)) {
                return ResultTool.error();
            }
            StringBuilder builder = new StringBuilder();
            result.addResult("data", builder.append(domain).append(key).toString());
            return ResultTool.success(result);
        } catch (Throwable e) {
            log.error("ImageUploadController.uploadImage error", e);
            return ResultTool.exception(e);
        }
    }
}
