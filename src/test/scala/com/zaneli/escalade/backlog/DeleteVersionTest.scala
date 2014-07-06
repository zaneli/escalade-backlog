package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeleteVersionTest extends Specification with Mockito with TestUtil {

  "プロジェクトの発生バージョン/マイルストーンを削除" should {

    "発生バージョン/マイルストーンIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("DeleteVersion.xml", request)
        val result = client.deleteVersion(123)
        getActualRequest(request) must_== getExpectedRequest("DeleteVersion.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Version
  def assertResponse(version: Version) = {
      version.id must_== 1234
      version.name must_== "リリースバージョン1.0"
      version.startDate.get must_== getDate("20100131")
      version.dueDate.get must_== getDate("20100315")
      version.archived.get must beTrue
  }
}