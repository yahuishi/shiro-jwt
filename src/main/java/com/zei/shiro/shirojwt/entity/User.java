package com.zei.shiro.shirojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    private Integer id;

    private String username;

    private String password;

    private String role;

    private String permission;
}
