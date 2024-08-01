package com.fushun.framework.sample.security.manageapp.model;

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
public class AppUserLoginInfo extends BaseLoginInfo implements JsonGraalVMNativeBean, IBeanCopyPropertiesBean {

    private String mobile;
    private String token;

    private String openId;
    //小程序open id
    private String wxAppOpenId;
    //设备id
    private String deviceId;
    private Set<GrantedAuthority> authorities;

    public AppUserLoginInfo(){

    }

    public AppUserLoginInfo(UserDTO user) {
        this.userName=user.getUserAccount();
        this.password = user.getUserPassword();
        this.nickName = user.getNickName();
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.permissions = user.getPermissions();
        this.authorities = user.getAuthorities();
        this.appUserToken = user.getAppUserToken();
    }

    /**
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> list= new HashSet<>();
        list.add(new SimpleGrantedAuthority("ROLE_APP"));
        return list;
    }

}
