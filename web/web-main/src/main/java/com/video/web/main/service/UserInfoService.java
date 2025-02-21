package com.video.web.main.service;

import com.video.model.entity.Unread;
import com.video.model.entity.UserInfo;
import com.video.model.entity.UserStats;
import com.video.web.main.vo.RegisterVo;
import com.video.web.main.vo.UserInfoQueryVo;
import com.video.web.main.vo.UserStatsQueryVo;

public interface UserInfoService {
    UserInfo getById(Long id);

    String insertOne(RegisterVo registerVo);

    Unread getUnreadById(Long uid);

    UserStats getUserStatsByUid(Long uid);

    void updateNotice(UserStatsQueryVo queryVo);

    void updateDescription(UserInfoQueryVo queryVo);

    void updateUserStats(Long uid);

    void updateUserInfo(UserInfoQueryVo queryVo);
}
