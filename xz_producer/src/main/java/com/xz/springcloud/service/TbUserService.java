package com.xz.springcloud.service;

import com.xz.springcloud.domain.TbUser;

import java.util.List;

public interface TbUserService{

    TbUser selectById(Integer id);

    void insertAll(List<TbUser> users);

    TbUser findOne();
}
