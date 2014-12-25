package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.request.AddVersionParamBuilder

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class AddVersionTest extends Specification with Mockito with TestUtil {

  "プロジェクトの発生バージョン/マイルストーンを追加" should {

    "プロジェクトIDと発生バージョン/マイルストーン名のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("AddVersion.xml", request)
        val result = client.addVersion(AddVersionParamBuilder(12, "リリースバージョン1.0").build)
        getActualRequest(request) must_== getExpectedRequest("AddVersionByProjectIdName.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("AddVersion.xml", request)
        val result = client.addVersion(AddVersionParamBuilder(12, "リリースバージョン1.0")
            .startDate(getDate("20100131")).dueDate(getDate("20100315")).build)
        getActualRequest(request) must_== getExpectedRequest("AddVersionByAllContents.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Version
  def assertResponse(version: Version) = {
      version.id must_== 733
      version.name must_== "リリースバージョン1.0"
      version.startDate.get must_== getDate("20100131")
      version.dueDate.get must_== getDate("20100315")
      version.archived.get must beFalse
  }
}