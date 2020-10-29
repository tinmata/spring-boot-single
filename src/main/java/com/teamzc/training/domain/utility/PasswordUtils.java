package com.teamzc.training.domain.utility;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードのハッシュ化処理です。
 * <pre>
 *   Spring Securityではデフォルトで「DelegatingPasswordEncoder」を使用します。
 * </pre>
 */
public class PasswordUtils {

  public static void main(String[] args) {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    System.out.println(passwordEncoder.encode("weakP@ss"));
  }
}
