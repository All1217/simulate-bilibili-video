package com.video.web.main.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Schema(description = "用户与视频交互Vo")
public class UserVideoQueryVo {
    @Schema(description = "主键")
    @TableField(value = "id")
    private Long id;

    @Schema(description = "用户唯一标识")
    @TableField(value = "uid")
    private Long uid;

    @Schema(description = "视频标识")
    @TableField(value = "vid")
    private Long vid;

    @Schema(description = "交互类型")
    @TableField(value = "action_type")
    private Integer actionType;

    @Schema(description = "交互时间")
    @TableField(value = "action_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date actionTime;
}
