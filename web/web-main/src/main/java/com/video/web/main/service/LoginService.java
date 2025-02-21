package com.video.web.main.service;

import com.video.model.entity.UserInfo;
import com.video.web.main.vo.LoginVo;

public interface LoginService {
    String login(LoginVo loginVo);

    UserInfo getLoginUserInfo(Long userId);
}
