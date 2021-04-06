package com.smart.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
@EnableWebSecurity
public class Myconfig extends WebSecurityConfigurerAdapter {
	@Bean
	public UserDetailsService getUserDetailsservice() {
		return new UserDetailsServiceImpl();
		
	}
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsservice());
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	//Configure Method
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	@Override
	protected void configure(HttpSecurity http)throws Exception {
		http.authorizeRequests().antMatchers("/admin**").hasRole("ADMIN").antMatchers("/**").permitAll().and().formLogin().
		loginPage("/reg").loginProcessingUrl("/dologin").defaultSuccessUrl("/admin/check").failureUrl("/login-fail")
		.and().csrf().disable();
		
		http.authorizeRequests().antMatchers("/user/**").
		hasRole("USER").antMatchers("/**").permitAll().and().formLogin().
		loginPage("/reg").loginProcessingUrl("/dologin").defaultSuccessUrl("/user/Dash").failureUrl("/login-fail")
		.and().csrf().disable();
	}
	
}
