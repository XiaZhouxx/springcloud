package com.xz.springcloud.service.impl;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.mapper.TbUserMapper;
import com.xz.springcloud.service.TbUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbUserServiceImpl implements TbUserService{
    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    @Transactional
    public void insertAll(List<TbUser> users) {
        tbUserMapper.insertAll(users);
    }

    @Override
    @Cacheable(value = "User", key = "#id")
    public TbUser selectById(Integer id) {
        System.out.println("未使用缓存....");
        return tbUserMapper.selectById(id);
    }

    @Override
    public TbUser findOne() {
        return tbUserMapper.findOne();
    }
}
