package com.teamzc.training.domain.entity;

import lombok.Data;

/**
 * 認証・認可ユーザ情報を保持するクラス。
 * <pre>
 *   認証成功後、アプリケーション側で使用することを目的としたユーザ情報です。
 * </pre>
 */
@Data
public class AuthUserInfo {

  private String userId;
  private String userName;
  private String email;
  private String password;
  private String role;
  private boolean enabled;
}
