package com.video.web.main.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "推荐服务Vo")
public class RecoQueryVo {
    @Schema(description="用户ID")
    private Long uid;

    @Schema(description="推荐视频数量")
    private Integer count;
}
