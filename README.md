#Backlog4j
[Backlog API](http://www.backlog.jp/api/ "Backlog API") の Scala ラッパーライブラリです。

##一般APIの使用
    val client = BacklogClientFactory(<スペースID>, <ユーザーID>, <パスワード>).createClient
    client.getProjects
    client.getUser(<ユーザID or ログインID>)
    client.findIssue(FindIssueParamBuilder(<プロジェクトID>).build)
    client.findIssue(
        FindIssueParamBuilder(<プロジェクトID>).componentId(<カテゴリID>).milestoneId(<マイルストーンID1>, <マイルストーンID2>).build)

##管理者向けAPIの使用
    val client = BacklogClientFactory(<スペースID>, <ユーザーID>, <パスワード>).createAdminClient
    client.getProjects