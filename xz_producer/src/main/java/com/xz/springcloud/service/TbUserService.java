package com.xz.springcloud.service;

import com.xz.springcloud.domain.TbUser;

public interface TbUserService{

    TbUser selectById(Integer id);
}
