package com.teamzc.training.domain.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TRN_A_PRODUCT")
/*
  Spring Data JPA 使用時に Hibernate がエンティティのプロキシオブジェクトに hibernateLazyInitializer というフィールドを追加されます。
  Jackson が hibernateLazyInitializer を JSON シリアライズできずにエラーになっています。
  getOneメソッドの際に"handler"と"hibernateLazyInitializer"というものが勝手に追加されます。
  @JsonIgnoreProperties({"hibernateLazyInitializer","handler"}) を追加して、
  シリアライズ対象から除外することで解決します。
 */
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
/*
  エンティティの遅延読み込みを無効にします。
 */
//@Proxy(lazy = false)
public class Product implements Serializable {

  @Id
  @Column(name = "PRODUCT_ID")
  private String productId;

  @Column(name = "PRODUCT_NAME")
  private String productName;

  @Column(name = "RELEASE_DATE")
  private Date releaseDate;

  @Column(name = "ACTRESS")
  private String actress;

  @Column(name = "SERIES")
  private String series;

  @Column(name = "MAKER")
  private String maker;

  @Column(name = "LABEL")
  private String label;

  @Column(name = "GENRE")
  private String genre;

}
