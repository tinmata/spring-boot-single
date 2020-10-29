package com.teamzc.training.controller;

import com.teamzc.training.domain.entity.Product;
import com.teamzc.training.domain.service.ProductService;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * コントロールクラスです。
 * <pre>
 *   Spring Dependency Injection
 *    - Constructor Injection
 *    - Filed Injection
 *    - Setter Injection
 * </pre>
 * <pre>
 *   Spring Bootで@Autowired(@Inject)を使うと下記のような警告が出るようになりました。
 *   「Field injection is not recommended.
 *     Spring Team recommends: "Always use constructor based dependency injection in your beans.
 *     Always use assertions for mandatory dependencies."」
 *   つまり、Spring Teamは、beanのインジェクションには常にコンストラクタインジェクションを使うように、と推奨しています。
 * </pre>
 */
@RestController
@RequestMapping("/products")
// @RequiredArgsConstructorは初期化されていないfinalなフィールドをパラメータに取るコンストラクタを生成します。
@RequiredArgsConstructor
// Spring4.2以前はコンストラクタ・インジェクションで@Autowiredを省略できないので、
// (onConstructor = @__(@Autowired))を追加する必要があります。
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@SuppressWarnings("unused")
public class MyRestController {

  // Spring4.3以降ではコンストラクタ・インジェクションで@Autowiredを省略できます。
  // @Autowired
  // @NonNullが付与されたフィールドはnullチェックが実行され、パラメータがnullの場合はNullPointerExceptionが投げられます。
  @NonNull
  private final ProductService productService;

  @Secured(value = "ROLE_ADMIN")
  @GetMapping
  @ResponseBody
  public List<Product> getAllProducts() {
    log.info("全件検索処理を行います。");
    return productService.findAllProducts();
  }

  @Secured("ROLE_STAFF")
  @GetMapping("{id}")
  @ResponseBody
  public Product getProduct(@PathVariable("id") String productId) {
    log.info("個別検索処理を行います。条件：「ProductId」、値：「{}」", productId);
    return productService.findById(productId);
  }
}
