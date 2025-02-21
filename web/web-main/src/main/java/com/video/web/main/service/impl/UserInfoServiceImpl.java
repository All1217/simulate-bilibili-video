package com.video.web.main.service.impl;

import com.video.common.utils.NormalUtil;
import com.video.model.entity.Unread;
import com.video.model.entity.UserInfo;
import com.video.model.entity.UserStats;
import com.video.web.main.mapper.UserInfoMapper;
import com.video.web.main.service.UserInfoService;
import com.video.web.main.vo.RegisterVo;
import com.video.web.main.vo.UserInfoQueryVo;
import com.video.web.main.vo.UserStatsQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper uiMapper;

    @Override
    public UserInfo getById(Long id) {
        return uiMapper.selectOneByUsername(id.toString());
    }

    @Override
    @Transactional
    public String insertOne(RegisterVo registerVo) {
        long temp = NormalUtil.randomUid();
        while (true) {
            UserInfo tempUser = uiMapper.selectOneByUsername(temp + "");
            if (tempUser == null) {
                break;
            }
            temp = NormalUtil.randomUid();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(temp);
        userInfo.setUsername(temp + "");
        userInfo.setAvatar("https://morton321.oss-cn-hangzhou.aliyuncs.com/%E5%B1%8F%E5%B9%95%E6%88%AA%E5%9B%BE%202025-02-11%20232613.png");
        userInfo.setNickname(registerVo.getNickname());
        userInfo.setPassword(DigestUtils.md5DigestAsHex(registerVo.getPassword().getBytes()));
        userInfo.setCreateDate(new Date());
        uiMapper.insert(userInfo);
        UserStats userStats = new UserStats();
        userStats.setUid(temp);
        uiMapper.inserUserStats(userStats);
        Unread unread = new Unread();
        unread.setUid(temp);
        uiMapper.insertUnread(unread);
        return "" + temp;
    }

    @Override
    public Unread getUnreadById(Long uid) {
        return uiMapper.getUnreadById(uid);
    }

    @Override
    public UserStats getUserStatsByUid(Long uid) {
        return uiMapper.getUserStatsByUid(uid);
    }

    @Override
    public void updateNotice(UserStatsQueryVo queryVo) {
        UserStats userStats = new UserStats();
        userStats.setUid(queryVo.getUid());
        userStats.setNotice(queryVo.getNotice());
        uiMapper.updateUserStats(userStats);
    }

    @Override
    public void updateDescription(UserInfoQueryVo queryVo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(queryVo.getUid());
        userInfo.setDescription(queryVo.getDescription());
        uiMapper.updateUserInfo(userInfo);
    }

    @Override
    @Transactional
    public void updateUserStats(Long uid){
        long fansCount = uiMapper.calFansCount(uid);
        int followCount = uiMapper.calFollowCount(uid);
        long likeCount = uiMapper.calLikeCount(uid);
        long playCount = uiMapper.calPlayCount(uid);
        int videoCount = uiMapper.calVideoCount(uid);
        //TODO
//        int collectionCount = uiMapper.calCollectionCount(uid);
        int collectCount = uiMapper.calCollectCount(uid);
        UserStats userStats = new UserStats();
        userStats.setUid(uid);
        userStats.setFansCount(fansCount);
        userStats.setFollowCount(followCount);
        userStats.setLikeCount(likeCount);
        userStats.setPlayCount(playCount);
        userStats.setVideoCount(videoCount);
        userStats.setCollectCount(collectCount);
        uiMapper.updateUserStats(userStats);
    }

    @Override
    public void updateUserInfo(UserInfoQueryVo queryVo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUid(queryVo.getUid());
        userInfo.setAvatar(queryVo.getAvatar());
        uiMapper.updateUserInfo(userInfo);
    }
}
