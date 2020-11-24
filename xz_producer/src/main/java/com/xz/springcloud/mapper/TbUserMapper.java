package com.xz.springcloud.mapper;

import com.xz.springcloud.domain.TbUser;

import java.util.List;

public interface TbUserMapper {
    TbUser selectById(Integer id);

    void insertAll(List<TbUser> users);

    TbUser findOne();
}