package com.video.web.main.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.video.common.result.Result;
import com.video.model.entity.Danmu;
import com.video.model.entity.Video;
import com.video.web.main.service.DanmuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "弹幕相关接口")
@RestController
@RequestMapping("/main/danmu")
public class DanmuContorller {
    @Autowired
    private DanmuService danmuService;

    @Operation(summary = "发送弹幕")
    @PostMapping("/post")
    public Result postDanmu(@RequestBody Danmu danmu) {
        danmuService.insertOne(danmu);
        return Result.ok();
    }

    @Operation(summary = "查询弹幕列表")
    @GetMapping("/public/getDanmuList")
    public Result<List<Danmu>> getDanmuList(@RequestParam Long vid) {
        LambdaQueryWrapper<Danmu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Danmu::getVid, vid);
        queryWrapper.eq(Danmu::getStatus, 1);
        List<Danmu> list = danmuService.list(queryWrapper);
        return Result.ok(list);
    }
}
