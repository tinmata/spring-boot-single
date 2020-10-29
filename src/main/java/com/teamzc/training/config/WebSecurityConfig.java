package com.teamzc.training.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Securityのセキュリティ設定クラスです。
 * <pre>
 *   Conrollerクラスまたはそのメソッドに@Securedアノテーションを付けることで、URLに関する権限を指定する事ができます。
 *   この場合、WebSecurityConfigで@EnableGlobalMethodSecurityアノテーションを付けておく必要がある。
 *   ユーザーに権限が無い場合、AccessDeniedExceptionが発生する。
 * </pre>
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
@SuppressWarnings("unused")
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

//  @Bean
//  @Override
//  protected UserDetailsService userDetailsService() {
//
//    log.info("InMemoryユーザ情報を初期化します。");
//
//    UserDetails admin = User.withUsername("admin")
//        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
//        .roles("ADMINISTRATOR")
//        .build();
//
//    UserDetails user = User.withUsername("user")
//        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("Password"))
//        .roles("USER", "ADMINISTRATOR")
//        .build();
//
//    return new InMemoryUserDetailsManager(Arrays.asList(user, admin));
//  }

}
