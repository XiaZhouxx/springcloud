package com.xz.springcloud.service.impl;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.mapper.TbUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author xz
 * @ClassName TestService
 * @Description
 * @date 2020/4/22 0022 16:10
 **/
@Service
public class TestService {
    @Autowired
    TbUserServiceImpl userService;

    @Autowired
    TbUserMapper mapper;

    /**
     * 在默认传播级别下调用别的service 和自身的都没有问题
     * 需要注意required_New
     */
    @Transactional
    public void test() {
        List<TbUser> users = Arrays.asList(new TbUser("掌声", new Random().nextInt(100)));
        userService.insertAll(users);
        test1(users);
        throw new RuntimeException();
    }

    @Transactional
    public void test1(List<TbUser> users) {
        mapper.insertAll(users);
    }
}
