package com.video.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Schema(description = "一对一聊天室")
@TableName(value = "chat")
@Data
public class Chat implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "唯一标识")
    @TableField(value = "id")
    private Long id;

    @Schema(description = "对象ID")
    @TableField(value = "user_id")
    private Long userId;

    @Schema(description = "发起者ID")
    @TableField(value = "another_id")
    private Long anotherId;

    @Schema(description = "是否删除")
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    @Schema(description = "未读数量")
    @TableField(value = "unread")
    private Integer unread;

    @Schema(description = "上一条消息的时间")
    @TableField(value = "latest_time")
    private Date latestTime;
}
