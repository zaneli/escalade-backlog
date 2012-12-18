package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetVersionsTest extends Specification with Mockito with TestUtil {

  "プロジェクトの発生バージョン/マイルストーンを取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetVersions.xml", request)
        val result = client.getVersions(1)
        getActualRequest(request) must_== getExpectedRequest("GetVersions.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Version
  def assertResponse(versions: Array[Version]) = {
    versions.size must_== 8;
    {
      val version = versions(0)
      version.id must_== 733
      version.name must_== "どこでもドア"
      version.date.get must_== getDate("20070728")
    }
    {
      val version = versions(1)
      version.id must_== 5
      version.name must_== "お披露目パーティ"
      version.date.get must_== getDate("21121010")
    }
    {
      val version = versions(2)
      version.id must_== 112
      version.name must_== "要件定義１．０"
      version.date.get must_== getDate("20500303")
    }
    {
      val version = versions(3)
      version.id must_== 494
      version.name must_== "ロボット破棄"
      version.date.get must_== getDate("20060531")
    }
    {
      val version = versions(4)
      version.id must_== 725
      version.name must_== "妹生産"
      version.date.get must_== getDate("20060731")
    }
    {
      val version = versions(5)
      version.id must_== 6
      version.name must_== "初期出荷"
      version.date.get must_== getDate("20121111")
    }
    {
      val version = versions(6)
      version.id must_== 712
      version.name must_== "バージョン1.10.10"
      version.date must beNone
    }
    {
      val version = versions(7)
      version.id must_== 914
      version.name must_== "9/30"
      version.date must beNone
    }
  }
}