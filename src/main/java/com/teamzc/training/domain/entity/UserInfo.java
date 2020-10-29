package com.teamzc.training.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * ユーザ情報クラス。
 */
@Data
@Entity
@Table(name = "MST_USER_INFO")
public class UserInfo {

  @Id
  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "USER_NAME")
  private String userName;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PASSWORD")
  private String password;

  @Column(name = "ROLE")
  private String role;

  @Column(name = "ENABLED")
  private boolean enabled;
}
