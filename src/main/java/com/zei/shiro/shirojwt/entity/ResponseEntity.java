package com.zei.shiro.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 返回页面的统一数据格式
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class ResponseEntity {

    //返回状态码
    private Integer code;

    //返回信息
    private String msg;

    //返回数据
    private Object data;
}
