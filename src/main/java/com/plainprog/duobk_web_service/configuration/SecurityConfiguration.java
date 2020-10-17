package com.plainprog.duobk_web_service.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@Configuration
@EnableOAuth2Sso
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/terms-conditions","/privacy-policy","/constructor/users/loginMobile**","/dictionary/**","/mobile/**","/constructor/constants/**","/constructor/constants/translation_script**","/constructor/author/getBiography**","/constructor/authors/getMenuItems**","/constructor/authors/getImage**","/constructor/books/getImage**","/constructor/books/getContent**","/constructor/books/getBookMenuItems**","/", "/login**", "/webjars/**", "/error**", "/logout**", "/logout/**","/logout")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/index").permitAll()
                .and().csrf().disable();//.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}
