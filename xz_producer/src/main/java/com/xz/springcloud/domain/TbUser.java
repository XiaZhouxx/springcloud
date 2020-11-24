package com.xz.springcloud.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TbUser implements Serializable {
    /**
     * ID
     */
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    private TbUserDetails details;

    private List<TbOrder> orders;

    public TbUser(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public TbUser() {
    }

    private static final long serialVersionUID = 1L;
}