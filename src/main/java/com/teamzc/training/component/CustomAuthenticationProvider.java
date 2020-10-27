package com.teamzc.training.component;

import com.teamzc.training.domain.entity.CustomUserDetail;
import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 認証・認可を返す認証プロバイダです。
 */
//@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication authentication) {

    Collection<? extends GrantedAuthority> authorities = Arrays.asList(
        new SimpleGrantedAuthority("AUTH001"),
        new SimpleGrantedAuthority("AUTH002"),
        new SimpleGrantedAuthority("ADMIN"),
        new SimpleGrantedAuthority("ROLE_ADMIN")
    );

    CustomUserDetail detail = new CustomUserDetail();
    detail.setHogeValue("hogeValue");
    detail.setHogeList(Arrays.asList("aaa", "bbb", "ccc"));

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken("YuChen", "hoge", authorities);
    token.setDetails(detail);

    return token;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return true;
  }
}
