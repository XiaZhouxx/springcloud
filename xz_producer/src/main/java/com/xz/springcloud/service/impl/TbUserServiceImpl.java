package com.xz.springcloud.service.impl;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.mapper.TbUserMapper;
import com.xz.springcloud.service.TbUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TbUserServiceImpl implements TbUserService{
    @Resource
    private TbUserMapper tbUserMapper;

    @Override
    public TbUser selectById(Integer id) {
        return tbUserMapper.selectById(id);
    }

}
