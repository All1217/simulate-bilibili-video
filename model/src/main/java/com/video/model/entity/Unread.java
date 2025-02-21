package com.video.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Schema(description = "未读信息表")
@TableName(value = "msg_unread")
@Data
public class Unread implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @TableField(value = "uid")
    private Long uid;

    @Schema(description = "回复我的")
    @TableField(value = "reply")
    private Integer reply;

    @Schema(description = "@我的")
    @TableField(value = "at")
    private Integer at;

    @Schema(description = "点赞我的")
    @TableField(value = "love")
    private Integer love;

    @Schema(description = "系统消息")
    @TableField(value = "system")
    private Integer system;

    @Schema(description = "私信")
    @TableField(value = "whisper")
    private Integer whisper;

    @Schema(description = "动态")
    @TableField(value = "dynamic")
    private Integer dynamic;
}
