package com.video.web.main.controller;

import com.video.common.result.Result;
import com.video.model.entity.Unread;
import com.video.model.entity.UserInfo;
import com.video.model.entity.UserStats;
import com.video.web.main.service.UserInfoService;
import com.video.web.main.vo.RegisterVo;
import com.video.web.main.vo.UserInfoQueryVo;
import com.video.web.main.vo.UserStatsQueryVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户相关接口")
@RestController
@RequestMapping("/main/user")
public class UserController {
    @Autowired
    private UserInfoService uiService;

    @Operation(summary = "按UID获取用户信息")
    @GetMapping("getById")
    public Result<UserInfo> getUserInfoById(@RequestParam Long uid) {
        UserInfo userInfo = uiService.getById(uid);
        return Result.ok(userInfo);
    }

    @Operation(summary = "按UID获取用户信息(免登录)")
    @GetMapping("/public/getById")
    public Result<UserInfo> getUserInfoByIdUnLogin(@RequestParam Long id) {
        UserInfo userInfo = uiService.getById(id);
        return Result.ok(userInfo);
    }

    @Operation(summary = "注册（新增用户）")
    @PostMapping("register")
    public Result<String> saveOrUpdate(@RequestBody RegisterVo registerVo) {
        String username = uiService.insertOne(registerVo);
        return Result.ok(username);
    }

    @Operation(summary = "统计未读消息")
    @GetMapping("/getUnreadById")
    public Result<Unread> getUnreadById(@RequestParam Long uid) {
        Unread res = uiService.getUnreadById(uid);
        return Result.ok(res);
    }

    @Operation(summary = "获取用户额外信息")
    @GetMapping("/getUserStatsByUid")
    public Result<UserStats> getUserStatsByUid(@RequestParam Long uid) {
        UserStats res = uiService.getUserStatsByUid(uid);
        return Result.ok(res);
    }

    @Operation(summary = "修改公告")
    @PostMapping("/updateNotice")
    public Result updateNotice(@RequestBody UserStatsQueryVo queryVo) {
        uiService.updateNotice(queryVo);
        return Result.ok();
    }

    @Operation(summary = "修改个性签名")
    @PostMapping("/updateDescription")
    public Result updateDescription(@RequestBody UserInfoQueryVo queryVo) {
        uiService.updateDescription(queryVo);
        return Result.ok();
    }

    @Operation(summary = "更新数据")
    @GetMapping("/updateMultiStats")
    public Result updateMultiStats(@RequestParam Long uid) {
        uiService.updateUserStats(uid);
        return Result.ok();
    }

    @Operation(summary = "更新用户信息")
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfoQueryVo queryVo) {
        uiService.updateUserInfo(queryVo);
        return Result.ok();
    }
}
