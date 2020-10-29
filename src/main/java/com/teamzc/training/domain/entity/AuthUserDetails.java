package com.teamzc.training.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ユーザ情報のカスタマイズするクラスです。
 * <pre>
 *   Spring側のインターフェス「org.springframework.security.core.userdetails.UserDetails」を実装します。
 * </pre>
 */
public class AuthUserDetails implements UserDetails {

  private final AuthUserInfo authUserInfo;

  private final Collection<GrantedAuthority> authorities;

  public AuthUserDetails(AuthUserInfo authUserInfo) {
    this.authUserInfo = authUserInfo;
    this.authorities = new ArrayList<>();
    // 権限の設定
    // 権限は複数所持を考慮した設計になっているため、Collectionとして定義されています。
    // また権限の文字列の前には”ROLE_”を付与します。
    // 例えば設定クラスでhasRole(“ADMIN”)などと設定している場合、内部的には”ROLE_ADMIN”で権限の判定が行われます。
    // 必要がなければ、”ROLE_”を付与しません。
    this.authorities.add(new SimpleGrantedAuthority(authUserInfo.getRole()));
  }

  /**
   * ユーザ認証情報を返します。
   * <pre>
   *   getUsername()には認証で使用する項目を指定します。
   * </pre>
   *
   * @return ユーザ認証情報
   */
  public AuthUserInfo getAuthUserInfo() {
    return authUserInfo;
  }

  /**
   * 権限情報を持つCollectionを返します。
   *
   * @return 権限情報
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  /**
   * パスワードの値を返します。
   *
   * @return パスワード
   */
  @Override
  public String getPassword() {
    return authUserInfo.getPassword();
  }

  /**
   * キーの値を返します。
   *
   * @return 認証キー(ユーザID)
   */
  @Override
  public String getUsername() {
    return authUserInfo.getUserName();
  }

  /**
   * アカウントが有効期限内であるかを返します。
   *
   * @return アカウント有効期限フラグ
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  /**
   * アカウントがロックされていないかを返します。
   *
   * @return ロック状態フラグ
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  /**
   * 資格情報が有効期限内であるかを返します。
   *
   * @return 資格情報有効期限フラグ
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  /**
   * 有効なアカウントであるかを返します。
   *
   * @return 有効性フラグ
   */
  @Override
  public boolean isEnabled() {
    return authUserInfo.isEnabled();
  }
}
