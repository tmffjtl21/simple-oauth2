package com.tjlee.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * AuthorizationServerConfigurerAdapter 클래스를 상속하고 클래스 레벨에 @EnableAuthorizationServer 어노테이션을 추가하면 구체적인 환경 설정이 가능하다.
 * */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String CLIEN_ID = "tjlee-client";
    private static final String CLIENT_SECRET = "tjlee-secret";
    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String IMPLICIT = "implicit";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String TRUST = "trust";
    private static final int ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60;
    private static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

    private TokenStore tokenStore;
    private final AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    public AuthorizationServerConfig(TokenStore tokenStore, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.tokenStore = tokenStore;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * configure(ClientDetailsServiceConfigurer clients) 메써드에서는 API의 요청 클라이언트 정보를 설정할 수 있다.
     * inMemory()는 클라이언트 정보를 메모리에 저장한다. 개발 환경에 적합하다. 반면 jdbc()는 데이터베이스에 저장한다. 운영 환경에 적합하다.
     * withClient()로 client_id 값을 설정한다. secret()은 client_secret 값을 설정한다.
     * scopes()는 scope 값을 설정한다.
     * authorizedGrantTypes()는 grant_type(access_token을 획득하기 위한 4가지 인증 방법) 값을 설정한다. 복수개를 저장할 수 있다.
     *  본 예제에서는 client_id, client_secret 만으로 access_token을 요청할 수 있는 client_credentials를 설정했다.
     * */
    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(CLIEN_ID)
                .secret(passwordEncoder.encode(CLIENT_SECRET))
                .authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT )
                .scopes(SCOPE_READ, SCOPE_WRITE, TRUST)
                .accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
                .refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager);
    }

    /**
     * ClientDetailsService 빈 설정
     *      client_id, client_secret 등을 저장하는 클라이언트 저장소에 대한 모든 CRUD는 ClientDetailsService 인터페이스로 구현하게 되어 있다.
     *      기본 제공되는 구현체로는 InMemoryClientDetailService, JdbcClientDetailService 클래스가 제공된다. JDBC 기반의 빈 설정을 예로 들면 아래와 같다.
     * */
//    @Override
////    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
////        clients.withClientDetails(clientDetailsService());
////    }
////
////    @Bean
////    public ClientDetailsService clientDetailsService(){
////        return new JdbcClientDetailsService(someDataSource);
////    }
}
