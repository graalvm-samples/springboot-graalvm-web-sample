package com.fushun.framework.sample.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fushun.framework.sample.mybatis.enums.SexEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private SexEnum sex;
}
