package com.xz.springcloud.mapper;

import com.xz.springcloud.domain.TbUser;

public interface TbUserMapper {
    TbUser selectById(Integer id);
}