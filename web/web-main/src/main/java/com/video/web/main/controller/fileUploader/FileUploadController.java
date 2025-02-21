package com.video.web.main.controller.fileUploader;

import com.video.common.result.Result;
import com.video.common.utils.AliOssUtil;
import com.video.model.entity.Video;
import com.video.web.main.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Tag(name = "文件上传")
@RequestMapping("/main/file")
@RestController
public class FileUploadController {
    @Autowired
    private AliOssUtil aliOssUtil;
    @Autowired
    private VideoService videoService;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws IOException {
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //截取原始文件名后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String objectName = UUID.randomUUID() + extension;
        String filePath = aliOssUtil.upload(file.getBytes(), objectName);
        return Result.ok(filePath);
    }

    @Operation(summary = "投稿")
    @PostMapping("submit")
    public Result submit(@RequestBody Video video) {
        log.info("接收到的参数：{}", video);
        videoService.insertOne(video);
        return Result.ok();
    }
}
