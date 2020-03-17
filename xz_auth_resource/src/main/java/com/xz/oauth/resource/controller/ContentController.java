package com.xz.oauth.resource.controller;

import com.xz.oauth.resource.domain.TbContent;
import com.xz.oauth.resource.service.impl.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xz
 * @ClassName ContentController
 * @Description
 * @date 2020/2/14 0014 19:12
 **/
@RestController
public class ContentController {
    @Autowired
    TbContentService tbContentService;

    @RequestMapping("/")
    public List<TbContent> findAll() {
        return tbContentService.findAll();
    }
}
