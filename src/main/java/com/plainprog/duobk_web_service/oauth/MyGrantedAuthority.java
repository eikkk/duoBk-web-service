package com.plainprog.duobk_web_service.oauth;

import org.springframework.security.core.GrantedAuthority;

public class MyGrantedAuthority implements GrantedAuthority {
    private String authority;
    public MyGrantedAuthority(String authority){this.authority = authority;}
    @Override
    public String getAuthority() {
        return authority;
    }
}
