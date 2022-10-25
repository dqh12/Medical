package com.henu.oss.controller;

import com.henu.common.util.Result;
import com.henu.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "oss对象存储")
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Autowired
    private FileService fileService;

    /**
     * 上传文件到阿里云oss
     * @param file
     * @return
     */
    @ApiOperation(value = "上传文件到阿里云oss")
    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file) {
        String url = fileService.upload(file);
        return Result.ok(url);
    }
}
