#escalade-backlog
[Backlog](http://www.backlog.jp/ "Backlog") API の Scala ラッパーライブラリです。

[![Build Status](https://travis-ci.org/zaneli/escalade-backlog.png?branch=master)](https://travis-ci.org/zaneli/escalade-backlog)

##一般APIの使用
    val client = BacklogClientFactory(<スペースID>, <ユーザーID>, <パスワード>).createClient
    val projects = client.getProjects
    val user = client.getUser(<ユーザID or ログインID>)
    val issue1 = client.findIssue(FindIssueParamBuilder(<プロジェクトID>).build)
    val issue2 = client.findIssue(
        FindIssueParamBuilder(<プロジェクトID>).componentId(<カテゴリID>)
        .milestoneId(<マイルストーンID1>, <マイルストーンID2>).build)

##管理者向けAPIの使用
    val client = BacklogClientFactory(<スペースID>, <ユーザーID>, <パスワード>).createAdminClient
    val projects = client.getProjects

その他、[API リファレンス](http://www.backlog.jp/api/ "Backlog API リファレンス")を参照下さい。

## Maven Repository

* escalade-backlog は Scala 2.10.x, Scala 2.11.x に対応しています。

### pom.xml
    <repositories>
      <repository>
        <id>com.zaneli</id>
        <name>Zaneli Repository</name>
        <url>http://www.zaneli.com/repositories</url>
      </repository>
    </repositories>

    <dependencies>
      <dependency>
        <groupId>com.zaneli</groupId>
        <artifactId>escalade-backlog_2.11</artifactId>
        <version>0.0.1</version>
      </dependency>
    </dependencies>

### build.sbt
    resolvers += "Zaneli Repository" at "http://www.zaneli.com/repositories"

    libraryDependencies ++= {
      Seq("com.zaneli" %% "escalade-backlog" % "0.0.1" % "compile")
    }
