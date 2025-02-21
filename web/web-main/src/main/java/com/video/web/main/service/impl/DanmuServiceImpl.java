package com.video.web.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.model.entity.Danmu;
import com.video.web.main.mapper.DanmuMapper;
import com.video.web.main.service.DanmuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DanmuServiceImpl extends ServiceImpl<DanmuMapper, Danmu> implements DanmuService {

    @Autowired
    private DanmuMapper danmuMapper;

    @Override
    public void insertOne(Danmu danmu) {
        log.info("danmu: {}", danmu);
        danmu.setId(0L);
        danmuMapper.insert(danmu);
    }
}
