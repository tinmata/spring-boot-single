package com.teamzc.training;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

/**
 * Main Application Class.
 * <pre>
 *   SpringBootServletInitializerクラスを拡張し、configure()メソッドをオーバーライドすることによって、
 *   サーブレットコンテナによって実行されるアプリケーションを設定することもできます。
 * </pre>
 */
/*
 【@EnableAutoConfiguration】
   @SpringBootApplicationを使用したくない場合、
   @EnableAutoConfigurationおよび@ComponentScanアノテーションはその動作を定義するため、代わりに使用することもできます。
   例えば、Kotlinで構築する場合、@SpringBootApplicationは使用できない。
 */
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
/*
 【@ComponentScan】
   引数なしで@ComponentScanを追加すると、
   @ComponentScanを使用して、@Configurationクラスを含むすべてのSpringコンポーネントはSpringBeanとして自動的に登録されます。
   （@Component、@Service、@Repository、@Controllerなど）
   また、XMLベースの構成を使用する必要がある場合は、@Configurationクラスから開始することをお勧めします。
   @ImportResourceアノテーションを使用してXML構成ファイルをロードできます。
 */
/*
 【@EnableAutoConfiguration】
   自動構成を有効にしたい場合、
   @EnableAutoConfigurationまたは@SpringBootApplicationアノテーションを@Configurationクラスの1つに追加する必要があります。
   ユーザーが情報を受け取る際や自らに関する情報を利用される際などに、
   Opt-in: 許諾（パーミッション）の意思を示す行為を「オプトイン」という。
   Opt-out: 反対に許諾しない意思を示す行為を「オプトアウト」という。
   特定の自動構成クラスを無効にしたい場合、
   @SpringBootApplicationのexclude属性を使用して無効にすることができます。
   例えば、@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
 */
@PropertySource(value = "file:./config/ext-properties.properties", encoding = "UTF-8")
public class Application extends SpringBootServletInitializer {

  /**
   * Jarパッケージ化する場合、埋め込みコンテナから実行できます。
   *
   * @param args
   */
  public static void main(String[] args) {
    configureApplication(new SpringApplicationBuilder()).run(args);
  }

  /**
   * SpringApplicationBuilderのカスタマイズ処理です。
   *
   * @param builder
   * @return
   */
  private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
    return builder.sources(Application.class)
        // 起動時に出力されるバナーを生成しない
        .bannerMode(Banner.Mode.OFF);
  }

  /**
   * Webコンテナにデプロイ時に作成するWARアーカイブからSpringApplicationを実行します。
   *
   * @param builder
   * @return
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return configureApplication(builder);
  }
}
