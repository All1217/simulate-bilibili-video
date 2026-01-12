package com.video.web.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.video.model.entity.Danmu;

public interface DanmuService extends IService<Danmu> {
    void insertOne(Danmu danmu);
}
