package com.video.web.main.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.video.common.result.Result;
import com.video.model.entity.UserTag;
import com.video.model.enums.TagType;
import com.video.web.main.mapper.UserTagMapper;
import com.video.web.main.service.UserTagService;
import com.video.web.main.vo.RecQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.video.common.result.ResultCodeEnum.PARAM_ERROR;

@Service
public class UserTagServiceImpl extends ServiceImpl<UserTagMapper, UserTag> implements UserTagService {

    @Override
    public Result<List<Long>> filterUserByTags(RecQueryVo queryVo) {
        if (queryVo == null) {
            return Result.fail(PARAM_ERROR.getCode(), "请求参数不能为空");
        }
        List<Long> userIds = queryVo.getUids();
        Integer tagCode = queryVo.getTag();
        if (userIds == null || userIds.isEmpty()) {
            return Result.fail(PARAM_ERROR.getCode(), "用户ID列表不能为空");
        }
        if (tagCode == null) {
            return Result.fail(PARAM_ERROR.getCode(), "标签代码不能为空");
        }
        String tagName = convertCodeToTagName(tagCode);
        if (!StringUtils.hasText(tagName)) {
            return Result.fail(PARAM_ERROR.getCode(), "无效的标签代码: ");
        }
        List<Long> filteredUserIds = lambdaQuery()
                .select(UserTag::getUid)
                .in(UserTag::getUid, userIds)
                .eq(UserTag::getTagName, tagName)
                .gt(UserTag::getWeight, 0)
                .list()
                .stream()
                .map(UserTag::getUid)
                .distinct()
                .toList();
        return Result.ok(filteredUserIds);
    }

    private String convertCodeToTagName(Integer code) {
        for (TagType tagType : TagType.values()) {
            if (tagType.getCode().equals(code)) {
                return tagType.getTagName();
            }
        }
        return null;
    }
}
