package com.video;

import com.video.model.entity.UserInfo;
import com.video.web.main.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest
public class QuickTest {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    void contextLoads() {
        // 这个测试只检查 Spring 上下文是否能正常加载
        log.info("Spring 上下文加载成功！");
    }

    @Test
    public void getUserInfoByIdUnLogin() {
        UserInfo userInfo = userInfoMapper.getById(123123123L);
        log.info("userInfo: {}", userInfo);
    }

    @Test
    void testRedisConnection() {
        redisTemplate.opsForValue().set("test", "123");
        String res = redisTemplate.opsForValue().get("test").toString();
        if (res != null) {
            log.debug("拿到数据：{}", res);
        } else {
            log.error("发生错误！");
        }
    }
}
