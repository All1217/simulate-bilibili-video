package com.video.web.main.controller.chat;

import com.video.common.login.LoginUserHolder;
import com.video.common.result.Result;
import com.video.model.entity.Chat;
import com.video.model.entity.UserInfo;
import com.video.web.main.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "聊天室接口")
@RestController
@RequestMapping("/main/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @Operation(summary = "按ID获取聊天室信息")
    @GetMapping("info")
    public Result<Chat> info() {
        Chat chat = chatService.getById(1L);
        return Result.ok(chat);
    }
}
