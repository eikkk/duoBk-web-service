package com.plainprog.duobk_web_service.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MyAuthorityExtractor implements AuthoritiesExtractor {
    @Value("${duobk.superadmins}")
    private String[] superAdmins;
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        String mail = (String) map.get("email");

        String authoritiesStr = "ROLE_USER,ROLE_ADMIN,ROLE_SUPERADMIN";
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        String[] stringAuthorities =authoritiesStr.split(",");
        for(int i =0; i< stringAuthorities.length; i++)
            authorities.add(new MyGrantedAuthority(stringAuthorities[i]));
        return authorities;
    }
}
