package com.xz.springcloud.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbUserDetails implements Serializable {
    private Integer dId;

    private Integer userId;

    private String address;

    private String phone;

    private static final long serialVersionUID = 1L;
}