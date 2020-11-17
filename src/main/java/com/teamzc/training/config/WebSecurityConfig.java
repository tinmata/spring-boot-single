package com.teamzc.training.config;

import com.teamzc.training.domain.service.UserDetailsManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
// Lombokによりfinalメンバー付きのコンストラクタを自動生成する。
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Slf4j
@SuppressWarnings("unused")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsManager userDetailsManager;

  /**
   * パスワードのハッシュ化
   * <pre>
   *   BCrypt は単純に入力を１回ハッシュ化するのではなく、
   *   ランダムなソルトを付与したうえで複数回ハッシュ化を適用することで元のパスワードを推測しづらくする。
   *   Spring Security もこの BCrypt を利用することを推奨している。
   *   パスワードのハッシュ化は、 PasswordEncoder を DaoAuthenticationProvider に設定することで実現できる。
   *   BCrypt を使う場合は、 BCryptPasswordEncoder という PasswordEncoder の実装を使用する。
   * </pre>
   *
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        // "/"と"/home"はすべてのユーザがリクエスト可
        .antMatchers("/home", "/encode-password/{password}").permitAll()
        // "/users/create"はOWNERとMANAGER権限のみリクエスト可
        .antMatchers("/users/create").hasAnyRole("OWNER", "MANAGER")
        // "/users/delete"はOWNER権限のみリクエスト可
        .antMatchers("/users/delete/{id}").hasRole("OWNER")
        // 認証済みユーザのみがリクエスト可
        .anyRequest().authenticated()
        // FormLoginConfigurer.javaをnewして、Form認証を有効にする
        .and().formLogin()
        // ログインページのパスは"/login"で、すべてのユーザがリクエスト可
        .loginPage("/login").permitAll()
        // ログイン成功後、デフォルトでは"/users"にリダイレクトする
        //.defaultSuccessUrl("/users")
        // 第二引数で常にデフォルトターゲットに遷移するようにする
        .defaultSuccessUrl("/success", true)
        // 認可エラー時の制御
        // .and()
        // .exceptionHandling().accessDeniedPage("/access-denied")
        // または、「AccessDeniedHandler」を実装して制御する
        // .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
        // ログアウトを有効にする、ログアウト成功後のリダイレクト先は"/login"
        .and().logout().logoutSuccessUrl("/login").permitAll()
        // CSRF対策機能を無効にする
        .and().csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 認証時に使うUserDetailsService.javaの実装を設定、利用するPasswordEncoder.javaを設定
    auth.userDetailsService(userDetailsManager).passwordEncoder(passwordEncoder());
  }

  //  /**
//   * InMemoryUser認証情報設定処理です。
//   */
//  @Bean
//  @Override
//  protected UserDetailsService userDetailsService() {
//
//    log.info("InMemoryユーザ情報を初期化します。");
//
//    UserDetails admin = User.withUsername("admin")
//        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin"))
//        .roles("ADMINISTRATOR", "USER")
//        .build();
//
//    UserDetails user = User.withUsername("user")
//        .password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("Password"))
//        .roles("ADMINISTRATOR")
//        .build();
//
//    return new InMemoryUserDetailsManager(Arrays.asList(user, admin));
//  }

}
