package com.xz.springcloud.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.xz.springcloud.mapper.TbUserDetailsMapper;
import com.xz.springcloud.service.TbUserDetailsService;
@Service
public class TbUserDetailsServiceImpl implements TbUserDetailsService{

    @Resource
    private TbUserDetailsMapper tbUserDetailsMapper;

}
