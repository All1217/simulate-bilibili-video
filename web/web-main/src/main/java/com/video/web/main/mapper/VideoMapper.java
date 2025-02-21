package com.video.web.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.video.model.entity.UserVideo;
import com.video.model.entity.Video;
import com.video.model.entity.VideoStats;
import com.video.web.main.vo.UserVideoQueryVo;
import com.video.web.main.vo.VideoQueryVo;
import com.video.web.main.vo.VideoVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface VideoMapper extends BaseMapper<Video> {
    @Insert("insert into video (uid, title, type, auth, duration, mc_id, sc_id, tags, descr, status, visible, cover_url, video_url, upload_date, delete_date) values (#{uid}, #{title}, #{type}, #{auth}, #{duration}, #{mcId}, #{scId}, #{tags}, #{descr}, #{status}, #{visible}, #{coverUrl}, #{videoUrl}, #{uploadDate}, #{deleteDate})")
    @Options(useGeneratedKeys = true, keyProperty = "vid", keyColumn = "vid")
    void insertOne(Video video);

    @Insert("insert into video_stats (vid, play, danmu, good, bad, coin, collect, share, comment) " +
            "VALUES (#{vid}, #{play}, #{danmu}, #{good}, #{bad}, #{coin}, #{collect}, #{share}, #{comment})")
    void inserOneStats(VideoStats videoStats);

    IPage<VideoVo> pageItem(IPage<VideoVo> page, VideoQueryVo queryVo);

    @Select("SELECT * from video where vid = #{vid} and visible = 0 and status = 1")
    Video getById(Long vid);

    @Select("select * from video_stats where vid = #{vid}")
    VideoStats getVideoStatsById(Long vid);

    @Select("select * from user_video where uid = #{uid} and vid = #{vid}")
    UserVideo selectActionById(Long uid, Long vid);

    @Insert("insert into user_video (uid, vid, play, love, unlove, coin, collect, play_time, love_time, coin_time) " +
            "values (#{uid}, #{vid}, #{play}, #{love}, #{unlove}, #{coin}, #{collect}, #{playTime}, #{loveTime}, #{coinTime})")
    void insertAction(UserVideo userVideo);

    void updateAction(UserVideo userVideo);

    VideoStats getStatsBySum(UserVideoQueryVo userVideoQueryVo);

    @Select("select * from user_video where vid = #{vid} and uid = #{uid}")
    UserVideo getInterActionStats(Long uid, Long vid);

    long calPlay(Long vid);
    long calDanmu(Long vid);
    long calLike(Long vid);
    long calCoin(Long vid);
    long calCollect(Long vid);
    long calComment(Long vid);

    void updateVideoStats(VideoStats videoStats);

    List<VideoVo> batchSelect(List<Long> videoIds);
}
