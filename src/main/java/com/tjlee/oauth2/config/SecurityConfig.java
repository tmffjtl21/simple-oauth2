package com.tjlee.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * 사용자 저장소 설정
 *      클라이언트와 토큰 관리는 Spring Security OAuth 모듈이 담당하지만 사용자 관리는 Spring Security의 몫이다.
 *      /oauth/authorize 요청을 처리하려면 사용자 저장소에 접근할 수 있어야 한다. 아래는 메모리에 사용자 정보를 등록하는 예이다.
 *
 *  auth.userDetailsService()로 임의의 UserDetailsService 인터페이스 구현체를 설정할 수 있다.
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource(name = "userService")
    private UserDetailsService userDetailsService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/api-docs/**").permitAll()
                .antMatchers("/h2-console/*").permitAll();
    }

    /**
     * TokenStore 빈 설정
     *      Spring Security OAuth에서 access_token, refresh_token을 저장하는 토큰 저장소에 대한 모든 CRUD는 TokenStore 인터페이스로 구현하게 되어 있다.
     *      기본 제공되는 구현체로는 InMemoryTokenStore, JdbcTokenStore, RedisTokenStore 클래스가 제공된다. 인터페이스만 구현하면 되므로 제3의 구현체를 작성해도 된다.
     *
     *      TokenStore 빈을 설정하려면 configure(AuthorizationServerEndpointsConfigurer endpoints) 오버라이드 메써드에서 endpoints.tokenStore()를 호출하면 된다.
     *      여기서는 InMemoryTokenStore
     * */
    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
