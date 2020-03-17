package com.xz.oauth.server.mapper;

import com.xz.oauth.server.domain.TbPermission;
import tk.mybatis.mapper.MyMapper;

import java.util.List;

public interface TbPermissionMapper extends MyMapper<TbPermission> {
    List<TbPermission> findByUserId(Long userId);
}