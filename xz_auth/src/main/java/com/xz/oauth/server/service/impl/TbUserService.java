package com.xz.oauth.server.service.impl;

import com.xz.oauth.server.domain.TbUser;
import com.xz.oauth.server.mapper.TbUserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
@Service
public class TbUserService{

    @Resource
    private TbUserMapper tbUserMapper;


    public TbUser getByUserName(String userName) {
        Example e = new Example(TbUser.class);
        e.createCriteria().andEqualTo("username", userName);

        return tbUserMapper.selectOneByExample(e);
    }


}
