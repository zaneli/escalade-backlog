#escalade-backlog
[Backlog](http://www.backlog.jp/ "Backlog") API �� Scala ���b�p�[���C�u�����ł��B

[![Build Status](https://travis-ci.org/zaneli/escalade-backlog.png?branch=master)](https://travis-ci.org/zaneli/escalade-backlog)

##���API�̎g�p
    val client = BacklogClientFactory(<�X�y�[�XID>, <���[�U�[ID>, <�p�X���[�h>).createClient
    val projects = client.getProjects
    val user = client.getUser(<���[�UID or ���O�C��ID>)
    val issue1 = client.findIssue(FindIssueParamBuilder(<�v���W�F�N�gID>).build)
    val issue2 = client.findIssue(
        FindIssueParamBuilder(<�v���W�F�N�gID>).componentId(<�J�e�S��ID>)
        .milestoneId(<�}�C���X�g�[��ID1>, <�}�C���X�g�[��ID2>).build)

##�Ǘ��Ҍ���API�̎g�p
    val client = BacklogClientFactory(<�X�y�[�XID>, <���[�U�[ID>, <�p�X���[�h>).createAdminClient
    val projects = client.getProjects

���̑��A[API ���t�@�����X](http://www.backlog.jp/api/ "Backlog API ���t�@�����X")���Q�Ɖ������B

## Maven Repository

* escalade-backlog �� Scala 2.10.x, Scala 2.11.x �ɑΉ����Ă��܂��B

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
