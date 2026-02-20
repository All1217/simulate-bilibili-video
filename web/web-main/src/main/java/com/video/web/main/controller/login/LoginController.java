package com.video.web.main.controller.login;

import com.video.common.login.LoginUserHolder;
import com.video.common.result.Result;
import com.video.model.entity.UserInfo;
import com.video.web.main.service.LoginService;
import com.video.web.main.vo.LoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Tag(name = "登录接口")
@RestController
@RequestMapping("/main")
public class LoginController {
    @Autowired
    private LoginService service;

    @Operation(summary = "登录")
    @PostMapping("login")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String jwt = service.login(loginVo);
        return Result.ok(jwt);
    }

    @Operation(summary = "获取登陆用户个人信息")
    @GetMapping("info")
    public Result<UserInfo> info() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        UserInfo userInfo = service.getLoginUserInfo(userId);
        return Result.ok(userInfo);
    }

//    @Operation(summary = "获取服务器本机IP地址")
//    @GetMapping("login/public/getIP")
//    public Result<String> getIP() throws UnknownHostException {
//        InetAddress res = service.getIP();
//        return Result.ok(res.getHostAddress());
//    }
}
