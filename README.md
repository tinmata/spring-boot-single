# Form認証で学ぶ

```text
Spring Security 入門
```

## Spring Security 概要

1. 登場する主なモジュール群

    + Core`認証と認可機能`
        + AccessDecisionManager
        + DaoAuthenticationProvider
    + Web`Webセキュリティ対策`
        + BasicAuthenticationFilter
        + DefaultHttpFirewall
    + Config`XMLネームスペース、JavaConfigサポート`
        + WebSecurityConfigurer
        + AuthenticationTrustResolver

2. フレームワークのアーキテクチャ（シーケンス）

    1. クライアントからリクエストが送られてくる
    2. FilterChainProxy.javaがリクエスト受け取る
    3. HttpServletRequestとHttpServletResponseに対してファイアウォール機能を組み込む
    4. SecurityFilterChainに設定されているSecurity Filterに処理を委譲する
    5. Security Filterが順に呼ばれて処理を行う
    6. 全Security Filterの処理が正常終了したら、Webアプリケーションリソースへアクセスできる

3. Secuity Filterの例

    1. LogoutFilter.java
        + ログアウト処理を行う
    2. UsernamePasswordAuthenticationFilter.java
        + Form認証で認証処理を行う
    3. BasicAuthenticationFilter.java
        + ベーシック認証で認証処理を行う
    4. ExceptionTranslationFilter.java
        + 認可処理で発生した例外をハンドリングし、クライアントへ適切なレスポンスを行う

## 今回作るもの

1. ユーザ管理システム
    + ユーザ一覧、詳細、登録、削除

2. 仕様
    + ページの参照には認証が必須
    + 権限は、OWNER > MANAGER > STAFF

        ||登録|一覧・詳細|削除|
        |:--:|:--:|:--:|:--:|
        |OWNER|可|可|可|
        |MANAGER|可|MANAGERとSTAFFのみ閲覧可|不可|
        |STAFF|不可|STAFFのみ閲覧可|不可|

## Form認証

1. 認証と認可

    + 認証
    ```text
    対象が、誰（何）であるかを確認すること
    ```
    + 認可
    ```text
    行動やリソースへのアクセスを許可すること
    ```

2. 実装するクラス

    + WebSecurityConfig.java
        + Spring Security 用の設定
    + AuthenticatedUser.java
        + UserDetailsインターフェースの実装。認証済のユーザ情報
    + UserDetailsManager.java
        + UserDetailsServiceインターフェースの実装。資格情報とユーザの状態をデータストアから取得

3. フォーム認証の流れ

    1. UsernamePasswordAuthenticationFilter処理
        1. ユーザ名とパスワード（資格情報）をリクエストパラメータとしてPOST
        2. UsernamePasswordAuthenticationFilterで資格情報を取得
        3. AuthenticationManagerに認証処理を委譲

    2. ProviderManager処理
        1. 登録されているAuthenticationProviderを順に呼ぶ

    3. DaoAuthenticationProvider処理
        1. 資格情報が正しいか確認を行う

    4. UserDetailsManager処理
        1. ユーザ情報を取得する。今回はDBから取得する。

    5. 認証後処理
        + 認証が成功したら、SavedRequestAwareAuthenticationSuccessHandlerが呼ばれて認証成功時の処理後にsuccessUrlにリダイレクト
        + 認証が失敗したら、SimpleUrlAuthenticationFailureHandlerが呼ばれて認証失敗時の処理後にfailureUrlにリダイレクト

4. 補足
    + 認証情報はセッションで管理
    ```text
    SessionContextPersistenceFilter.javaで既に認証済みかチェック
    ```
    + 認証失敗したユーザも認証情報は作られる
    ```text
    デフォルトでRoleがROLE_ANONYMOURSになる
    ```

5. 認可の流れ
    1. ExceptionTransactionFilter処理
        1. Spring Security 内で起きた例外をハンドリングして適切なレスポンスを行う。
            + 未認証ユーザからのアクセスの場合は認証を促すレスポンスを返す。
            + 認証済みユーザからのアクセスの場合は認可エラーを通知するレスポンスを返す。

    2. FilterSecurityInterceptor処理
        1. HTTPリクエストに対して認可処理を適用する。
        2. 認可処理はAccessDecisionManagerに委譲する。

    3. AccessDecision処理
        1. AccessDecisionMangerが登録されてあるAccessDecisionVoterを呼ぶ。
        2. AccessDecisionVoterでアクセス権の有無を投票する。
            + AccessDecisionVoter.java
                + アクセス権を付与するかを投票する。
                + WebExpressionVoter.javaがデフォルト適用
                ```java
                public interface AccessDecisionVoter<S> {
                  int ACCESS_GRANTED = 1;
                  int ACCESS_ABSTAIN = 0;
                  int ACCESS_DENIED = -1;
                }
              ```
            + AccessDecisionMangager.java
                + 投票結果から最終的なアクセス権を判断する。
                + AccessDecisionVoterを呼んでアクセス権を投票する。
                + 実装は３クラス。投票結果の扱い方が異なる。
                    + AffirmativeBased.java
                    ```text
                    全Voterのうち１つでも賛成すればアクセス許可
                    ```
                    + ConsensusBased.java
                    ```text
                    全Voterで賛成が多ければアクセス許可
                    ```
                    + UnanimousBased.java
                    ```text
                    全Voterが賛成ならアクセス許可
                    ```

    4. 認可処理後
        + 認可成功の場合のみリソースへアクセスできる。
        + 認可失敗の場合は、AccessDeniedExceptionが投げられる。
