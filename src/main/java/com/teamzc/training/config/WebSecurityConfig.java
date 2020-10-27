package com.teamzc.training.config;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // Authorize
        .authorizeRequests()
        .antMatchers("/", "/home").permitAll()
        .anyRequest().authenticated()
        .and()
        // Login
        .formLogin()
        // カスタマイズログイン画面追加
        .loginPage("/login").permitAll()
        .defaultSuccessUrl("/success")
        .and()
        // Logout
        .logout()
        .permitAll();
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {

    log.info("InMemoryユーザ情報を初期化します。");

    UserDetails admin = User.withUsername("admin")
        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
        .roles("ADMINISTRATOR")
        .build();

    UserDetails user = User.withUsername("user")
        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("Password"))
        .roles("USER", "ADMINISTRATOR")
        .build();

    return new InMemoryUserDetailsManager(Arrays.asList(user, admin));
  }

}
