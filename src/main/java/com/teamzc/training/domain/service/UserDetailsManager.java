package com.teamzc.training.domain.service;

import com.teamzc.training.domain.entity.AuthUserInfo;
import com.teamzc.training.domain.entity.AuthenticatedUser;
import com.teamzc.training.domain.reporitory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ユーザ認証サービスをカスタイマイズするクラスです。
 * <pre>
 *   Spring側のインターフェス「org.springframework.security.core.userdetails.UserDetailsService」を実装します。
 * </pre>
 */
@Service
@Transactional
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class UserDetailsManager implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

    if (null == userId || userId.isEmpty()) {
      throw new UsernameNotFoundException("User Id is empty.");
    }

    // [USER_ID]を条件としてユーザ情報を検索し、ユーザ認証情報に設定します。
    return userRepository.findById(userId).map(userInfo -> {
      AuthUserInfo authUserInfo = new AuthUserInfo();
      authUserInfo.setUserId(userInfo.getUserId());
      authUserInfo.setUserName(userInfo.getUserName());
      authUserInfo.setEmail(userInfo.getEmail());
      authUserInfo.setPassword(userInfo.getPassword());
      authUserInfo.setRole(userInfo.getRole());
      authUserInfo.setEnabled(userInfo.isEnabled());
      return new AuthenticatedUser(authUserInfo);
    }).orElseThrow(() -> new UsernameNotFoundException("Not found user id : " + userId));
  }

}
