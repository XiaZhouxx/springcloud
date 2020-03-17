package com.xz.springcloud.service.impl;

import com.xz.springcloud.domain.TbUser;
import com.xz.springcloud.mapper.TbOrderMapper;
import com.xz.springcloud.service.TbOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class TbOrderServiceImpl implements TbOrderService{

    @Resource
    private TbOrderMapper tbOrderMapper;


}
