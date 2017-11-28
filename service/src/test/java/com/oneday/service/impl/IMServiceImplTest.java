package com.oneday.service.impl;

import com.oneday.domain.po.User;
import com.oneday.service.IMService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author fanyongpeng [15104723@qq.com]
 * @version 1.0
 *          2017/7/11 16:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class IMServiceImplTest {
    @Resource
    private IMService iMService;

    /**
     * 测试Spring注解获取properties文件的值
     */
    @Test
    public void test() {
        System.out.println(iMService.create(null));
    }
    @Test
    public void update() {
        User user = new User();
        user.setId(1L);
        System.out.println(iMService.update(user).toString());
    }
    @Test
    public void refresh() {
        User user = new User();
        user.setId(1L);
        System.out.println(iMService.refresh(user).toString());
    }
}
