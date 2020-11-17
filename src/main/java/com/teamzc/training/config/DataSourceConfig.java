package com.teamzc.training.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * データベース接続カスタマイズ構成です。
 * <pre>
 *   外部プロパティファイルからデータソース設定値を取得して、DataSourceに設定します。
 * </pre>
 */
@Configuration
@PropertySource(value = "file:./config/datasource.properties", encoding = "UTF-8")
@SuppressWarnings("unused")
public class DataSourceConfig {

  @Autowired
  private Environment environment;

  /**
   * DataSourceを設定する処理です。
   * <pre>
   *   クラスパス配下のプロパティから取得する場合、
   *   @ConfigurationPropertiesアノテーションを使用します。
   *   prefixオプションを使用することで、url、username、passwordの個別設定は不要です。
   * </pre>
   */
//  @ConfigurationProperties(prefix = "app.datasource.hikari")
  @Bean
  public DataSource dataSource() {
    // Hikariを使用する場合、typeに追加します。
    return DataSourceBuilder.create().type(HikariDataSource.class)
        .url(environment.getProperty("app.datasource.url"))
        .username(environment.getProperty("app.datasource.username"))
        .password(environment.getProperty("app.datasource.password"))
        .build();
  }
}
