package com.video.web.main.service.impl;

import com.video.model.entity.Chat;
import com.video.web.main.mapper.ChatMapper;
import com.video.web.main.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatMapper chatMapper;

    @Override
    public Chat getById(Long i) {
        return chatMapper.selectById(i);
    }
}
