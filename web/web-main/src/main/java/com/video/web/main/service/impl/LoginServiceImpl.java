package com.video.web.main.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.video.common.exception.VideoException;
import com.video.common.result.ResultCodeEnum;
import com.video.common.utils.JwtUtil;
import com.video.model.entity.UserInfo;
import com.video.model.enums.BaseStatus;
import com.video.web.main.mapper.UserInfoMapper;
import com.video.web.main.service.LoginService;
import com.video.web.main.vo.LoginVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
//import org.apache.commons.codec.digest.DigestUtils;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public String login(LoginVo loginVo) {
        //校验用户是否存在
        UserInfo user = userInfoMapper.selectOneByUsername(loginVo.getUsername());
        if (user == null) {
            throw new VideoException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
        }
        //校验用户是否被禁
        if (Objects.equals(user.getStatus(), BaseStatus.DISABLE.getCode())) {
            throw new VideoException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
        } else if (Objects.equals(user.getStatus(), BaseStatus.LOGOUT.getCode())) {
            throw new VideoException(ResultCodeEnum.ADMIN_ACCOUNT_LOGOUT_ERROR);
        }
        //校验用户密码
        if (!user.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) {
            throw new VideoException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
        }
        //创建并返回TOKEN
        return JwtUtil.createToken(user.getUid(), user.getUsername());
    }

    @Override
    public UserInfo getLoginUserInfo(Long userId) {
        return userInfoMapper.getById(userId);
    }
}
