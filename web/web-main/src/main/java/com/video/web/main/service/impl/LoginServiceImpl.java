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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
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

    @Override
    public InetAddress getIP() throws UnknownHostException {
        try {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
                NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
                    InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
                    if (!inetAddr.isLoopbackAddress()) {// 排除loopback类型地址
                        if (inetAddr.isSiteLocalAddress()) {
                            // 如果是site-local地址，就是它了
                            return inetAddr;
                        } else if (candidateAddress == null) {
                            // site-local类型的地址未被发现，先记录候选地址
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null) {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            if (jdkSuppliedAddress == null) {
                throw new UnknownHostException("The JDK InetAddress.getLocalHost() method unexpectedly returned null.");
            }
            return jdkSuppliedAddress;
        } catch (Exception e) {
            UnknownHostException unknownHostException = new UnknownHostException(
                    "Failed to determine LAN address: " + e);
            unknownHostException.initCause(e);
            throw unknownHostException;
        }
    }
}