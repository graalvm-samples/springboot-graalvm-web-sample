package com.fushun.framework.sample.security.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fushun.framework.bean.properties.config.IBeanCopyPropertiesBean;
import com.fushun.framework.json.config.JsonGraalVMNativeBean;
import com.fushun.framework.security.model.BaseLoginInfo;
import com.fushun.framework.security.model.SimpleGrantedAuthority;
import com.fushun.framework.security.model.UserDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class AdminLoginInfo extends BaseLoginInfo implements JsonGraalVMNativeBean, IBeanCopyPropertiesBean {

    private String headPortrait;

    /**
     * 登陆端口 校验权限
     * 对应了不同的 HttpSecurity
     */
    private Set<GrantedAuthority> authorities;


    /**
     * APP交互token前缀key
     */
    private String appTokenPre;


    public AdminLoginInfo() {
    }

    public AdminLoginInfo(UserDTO user) {
        this.userName=user.getUserAccount();
        this.password = user.getUserPassword();
        this.nickName = user.getNickName();
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.headPortrait = user.getHeadPortrait();
        this.permissions = user.getPermissions();
        this.authorities = user.getAuthorities();
        this.appUserToken = user.getAppUserToken();
        this.appTokenPre = user.getAppTokenPre();
    }

    /**
     * 设置校验需要的权限列表 暂时听过写死的方式 为了解决方序列化回来 丢失的问题
     *
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> list= new HashSet<>();
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAdmin() {
        return userId != null && 1L == userId;
    }

}
