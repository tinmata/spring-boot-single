package com.teamzc.training.domain.service;

import com.teamzc.training.domain.entity.AuthUserDetails;
import com.teamzc.training.domain.entity.AuthUserInfo;
import com.teamzc.training.domain.reporitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 認証サービスクラスです。
 */
@Service
public class AuthUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String userId) {

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
      return new AuthUserDetails(authUserInfo);
    }).orElseThrow(() -> new UsernameNotFoundException("Not found user id : " + userId));
  }

}
