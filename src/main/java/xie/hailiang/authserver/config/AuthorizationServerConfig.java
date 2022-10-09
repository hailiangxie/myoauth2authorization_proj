package xie.hailiang.authserver.config;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hailiang XIE
 *
 */
@Slf4j
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	
	@Value("${jwtkey.password}")
	private String password;
	
	@Value("${jwtkey.privateKey}")
	private String privateKey;
	
	@Value("${jwtkey.alias}")
	private String alias;
	
	@Value("${oauth2.authorizationserver.redirect-uri}")
	private String redirectUri;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		log.info(">>>AuthorizationServerConfig.configure(AuthorizationServerEndpointsConfigurer endpoints) started");
		/**
		 * adding custom information to the jwt token
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		var tokenEnhancers = List.of(new CustomTokenEnhancer(), jwtAccessTokenConverter());
		tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
		**/
	    endpoints
	        .authenticationManager(authenticationManager)
	        .tokenServices(tokenServices());
	        //.tokenStore(tokenStore())
	        //.accessTokenConverter(jwtAccessTokenConverter());
	        //.tokenEnhancer(tokenEnhancerChain);
	}
	
	@Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("isAuthenticated()")
            	.checkTokenAccess("isAuthenticated()")
            	.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		log.info(">>> AuthorizationServerConfig.configure(ClientDetailsServiceConfigurer clients) started");
		clients.jdbc(dataSource);
		/**
		 * for DEV only
	    clients.inMemory()
	             .withClient("oauth2_client1")
	             .secret(passwordEncoder.encode("123456"))
	             .authorizedGrantTypes("password", "authorization_code", "refresh_token")
	             .scopes("read", "write")
	             .redirectUris(redirectUri)
	             .autoApprove(true)
	             .and()
	             .withClient("oauth2_client2")
	             .secret(passwordEncoder.encode("123456"))
	             .authorizedGrantTypes("password", "authorization_code", "refresh_token")
	             .scopes("read", "write")
	             .redirectUris(redirectUri)
	             .autoApprove(true);
	   **/
	}
	
	@Bean
	public TokenStore tokenStore() {
		//return new JwtTokenStore(jwtAccessTokenConverter());
		return new JdbcTokenStore(dataSource); // store the jwt token to database
	}
	
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
	    var converter = new JwtAccessTokenConverter();

	    KeyStoreKeyFactory keyStoreKeyFactory =
	       new KeyStoreKeyFactory(
	           new ClassPathResource(privateKey),
	                     password.toCharArray()
	       );

	    converter.setKeyPair(
	       keyStoreKeyFactory.getKeyPair(alias));

	    return converter;
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
	    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
	    defaultTokenServices.setTokenStore(tokenStore());
	    defaultTokenServices.setSupportRefreshToken(true);
	    defaultTokenServices.setTokenEnhancer(jwtAccessTokenConverter());
	    defaultTokenServices.setAccessTokenValiditySeconds(1296000);
	    return defaultTokenServices;
	}
}
