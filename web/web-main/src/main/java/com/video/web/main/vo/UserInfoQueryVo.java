package com.video.web.main.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户基本信息请求参数")
public class UserInfoQueryVo {
    @Schema(description = "ID")
    @TableField(value = "uid")
    private Long uid;

    @Schema(description = "头像")
    @TableField(value = "avatar")
    private String avatar;

    @Schema(description = "个性签名")
    @TableField(value = "description")
    private String description;
}
