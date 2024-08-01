package com.fushun.framework.sample.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fushun.framework.base.IBaseEnum;
import lombok.Getter;

@Getter
public enum SexEnum implements IBaseEnum<String> {
    BOY("BOY", "在线"),
    GIRL("GIRL", "离线");

    SexEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @EnumValue
    private String code;

    private String desc;
}
