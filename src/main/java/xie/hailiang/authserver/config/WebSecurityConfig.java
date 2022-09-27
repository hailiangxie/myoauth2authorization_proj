package xie.hailiang.authserver.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Hailiang XIE
 *
 */
@Slf4j
@Configuration
@Order(1)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
       return super.authenticationManagerBean();
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().passwordEncoder(passwordEncoder())
    	.dataSource(dataSource)
    	.usersByUsernameQuery("select username, password, enabled from authenticate_user where username=?")
    	.authoritiesByUsernameQuery("select username, role from authenticate_user where username=?");
		/**
		 * Only for dev
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("password")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("password")).roles("USER", "ADMIN");
        **/

    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		log.info(">>> WebSecurityConfig.configure(HttpSecurity http) started");
    	//HTTP Basic authentication
    	http
    			.authorizeRequests()
    			.antMatchers("/login", "/register", "/h2-console/**").permitAll()
    		.and()
    			.authorizeRequests()
    			.anyRequest().authenticated()
    		.and()
    		.formLogin()
    		.and()
    		.httpBasic();
    	
    	http.csrf().disable();
    	http.headers().frameOptions().disable();
    }
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
