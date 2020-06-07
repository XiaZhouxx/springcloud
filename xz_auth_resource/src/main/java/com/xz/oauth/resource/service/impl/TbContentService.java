package com.xz.oauth.resource.service.impl;

import com.xz.oauth.resource.domain.TbContent;
import com.xz.oauth.resource.mapper.TbContentMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbContentService{

    @Resource
    private TbContentMapper tbContentMapper;

    public List<TbContent> findAll() {
        return tbContentMapper.selectAll();
    }

    public TbContent findById(Long id) {
        return tbContentMapper.selectByPrimaryKey(id);
    }

}
