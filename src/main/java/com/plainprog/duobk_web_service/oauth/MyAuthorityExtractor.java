package com.plainprog.duobk_web_service.oauth;

import com.plainprog.duobk_web_service.services.UserService;
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
    //@Value("${duobk.superadmins}")
    private String[] superAdmins = {"petrodarchyn@gmail.com", "petrodarchyn2@gmail.com", "petrodarchyn3@gmail.com"};
    @Autowired
    UserService userService;
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {
        String mail = (String) map.get("email");

        String authoritiesStr = userService.getUserAuthorities(mail);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        if(authoritiesStr.equals(UserService.NO_SUCH_USER)){
            authoritiesStr = "ROLE_USER";
            for (String superAdmin : superAdmins) {
                if (mail.equals(superAdmin))
                    authoritiesStr = "ROLE_USER,ROLE_ADMIN,ROLE_SUPERADMIN";
            }
            userService.createUser(mail,authoritiesStr);
        }
        String[] stringAuthorities =authoritiesStr.split(",");
        for (String stringAuthority : stringAuthorities)
            authorities.add(new MyGrantedAuthority(stringAuthority));
        return authorities;
    }
}
