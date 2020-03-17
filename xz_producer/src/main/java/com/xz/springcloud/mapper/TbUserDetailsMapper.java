package com.xz.springcloud.mapper;

import com.xz.springcloud.domain.TbUserDetails;

public interface TbUserDetailsMapper {
    int deleteByPrimaryKey(Integer dId);

    int insert(TbUserDetails record);

    int insertSelective(TbUserDetails record);

    TbUserDetails selectByPrimaryKey(Integer dId);

    int updateByPrimaryKeySelective(TbUserDetails record);

    int updateByPrimaryKey(TbUserDetails record);
}