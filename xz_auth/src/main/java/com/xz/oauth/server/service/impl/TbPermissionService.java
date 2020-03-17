package com.xz.oauth.server.service.impl;

import com.xz.oauth.server.domain.TbPermission;
import com.xz.oauth.server.mapper.TbPermissionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbPermissionService{

    @Resource
    private TbPermissionMapper tbPermissionMapper;

    public List<TbPermission> findByUserId(Long userId) {
        return tbPermissionMapper.findByUserId(userId);
    }
}
