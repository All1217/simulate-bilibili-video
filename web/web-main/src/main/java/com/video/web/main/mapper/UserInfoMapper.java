package com.video.web.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.video.model.entity.Unread;
import com.video.model.entity.UserInfo;
import com.video.model.entity.UserStats;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo selectOneByUsername(String username);

    @Select("select * from user where uid = #{userId}")
    UserInfo getById(Long userId);

    @Select("SELECT * from msg_unread where uid = #{uid}")
    Unread getUnreadById(Long uid);

    @Select("select * from user_stats where uid = #{uid}")
    UserStats getUserStatsByUid(Long uid);

    void updateUserStats(UserStats userStats);

    void updateUserInfo(UserInfo userInfo);

    long calFansCount(Long uid);

    int calFollowCount(Long uid);

    long calLikeCount(Long uid);

    long calPlayCount(Long uid);

    int calVideoCount(Long uid);
    int calCollectCount(Long uid);
    void inserUserStats(UserStats query);

    void insertUnread(Unread unread);
}
