package com.xz.springcloud.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class TbOrder implements Serializable {
    private Integer id;

    private Integer userId;

    private String goods;

    private static final long serialVersionUID = 1L;
}