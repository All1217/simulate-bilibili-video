package com.video.web.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.video.model.entity.UserInfo;
import com.video.model.entity.UserVideo;
import com.video.model.entity.Video;
import com.video.model.entity.VideoStats;
import com.video.model.enums.ActionType;
import com.video.web.main.mapper.VideoMapper;
import com.video.web.main.mapper.VideoStatsMapper;
import com.video.web.main.service.VideoService;
import com.video.web.main.vo.UserVideoQueryVo;
import com.video.web.main.vo.VideoQueryVo;
import com.video.web.main.vo.VideoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VideoStatsMapper videoStatsMapper;

    @Override
    @Transactional
    public void insertOne(Video video) {
        video.setUploadDate(new Date());
        videoMapper.insertOne(video);
        VideoStats videoStats = new VideoStats();
        videoStats.setVid(video.getVid());
        videoStats.setPlay(0L);
        videoStats.setDanmu(0L);
        videoStats.setGood(0L);
        videoStats.setBad(0L);
        videoStats.setCoin(0L);
        videoStats.setCollect(0L);
        videoStats.setShare(0L);
        videoStats.setComment(0L);
        videoMapper.inserOneStats(videoStats);
    }

    @Override
    public IPage<VideoVo> pageItem(IPage<VideoVo> page, VideoQueryVo queryVo) {
        return videoMapper.pageItem(page, queryVo);
    }

    @Override
    public Video getById(Long vid) {
        return videoMapper.getById(vid);
    }

    @Override
    public VideoStats getVideoStatsById(Long vid) {
        return videoMapper.getVideoStatsById(vid);
    }

    @Override
    @Transactional
    public void recommend(UserVideoQueryVo userVideoQueryVo) {
        boolean flag = true;
        UserVideo userVideo = videoMapper.selectActionById(userVideoQueryVo.getUid(), userVideoQueryVo.getVid());
        if (userVideo == null) {
            userVideo = new UserVideo();
            userVideo.setVid(userVideoQueryVo.getVid());
            userVideo.setUid(userVideoQueryVo.getUid());
            userVideo.setCoin(0);
            userVideo.setLove(0);
            userVideo.setUnlove(0);
            userVideo.setCollect(0);
            userVideo.setPlay(0);
            userVideo.setPlayTime(new Date());
            flag = false;
        }
        if (Objects.equals(userVideoQueryVo.getActionType(), ActionType.LIKE.getNum())) {
            userVideo.setUnlove(0);
            if (userVideo.getLove() >= 1) {
                userVideo.setLove(0);
            } else {
                userVideo.setLove(1);
            }
            userVideo.setLoveTime(userVideoQueryVo.getActionTime());
        } else if (Objects.equals(userVideoQueryVo.getActionType(), ActionType.DISLIKE.getNum())) {
            userVideo.setLove(0);
            if (userVideo.getUnlove() >= 1) {
                userVideo.setUnlove(0);
            } else {
                userVideo.setUnlove(1);
            }
            userVideo.setLoveTime(userVideoQueryVo.getActionTime());
        } else if (Objects.equals(userVideoQueryVo.getActionType(), ActionType.COIN_ONE.getNum())) {
            if (userVideo.getCoin() >= 1) {
                userVideo.setCoin(0);
            } else {
                userVideo.setCoin(1);
            }
            userVideo.setCoinTime(userVideoQueryVo.getActionTime());
        } else if (Objects.equals(userVideoQueryVo.getActionType(), ActionType.COIN_TWO.getNum())) {
            if (userVideo.getCoin() >= 1) {
                userVideo.setCoin(0);
            } else {
                userVideo.setCoin(2);
            }
            userVideo.setCoinTime(userVideoQueryVo.getActionTime());
        } else if (Objects.equals(userVideoQueryVo.getActionType(), ActionType.PLAY.getNum())) {
            userVideo.setPlay(userVideo.getPlay() + 1);
            userVideo.setPlayTime(userVideoQueryVo.getActionTime());
        }
        if (flag) {
            videoMapper.updateAction(userVideo);
        } else {
            videoMapper.insertAction(userVideo);
        }
        updateVideoStats(userVideoQueryVo.getVid());
    }

    @Override
    public UserVideo getInterActionStats(UserVideoQueryVo userVideoQueryVo) {
        return videoMapper.getInterActionStats(userVideoQueryVo.getUid(), userVideoQueryVo.getVid());
    }

    @Transactional
    public void updateVideoStats(Long vid){
        long play = videoMapper.calPlay(vid);
        long collect = videoMapper.calCollect(vid);
        long danmu = videoMapper.calDanmu(vid);
        long like = videoMapper.calLike(vid);
        long comment = videoMapper.calComment(vid);
        long coin = videoMapper.calCoin(vid);
        VideoStats videoStats = new VideoStats();
        videoStats.setVid(vid);
        videoStats.setPlay(play);
        videoStats.setComment(comment);
        videoStats.setCollect(collect);
        videoStats.setCoin(coin);
        videoStats.setDanmu(danmu);
        videoStats.setGood(like);
        videoMapper.updateVideoStats(videoStats);
    }

    @Override
    @Transactional
    public List<VideoVo> getRealTimeRecommend(List<Long> videoIds) {
        return videoMapper.batchSelect(videoIds);
    }
}
