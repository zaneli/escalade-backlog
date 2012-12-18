package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.ColorType

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AddIssueTypeTest extends Specification with Mockito with TestUtil {

  "プロジェクトの課題種別を追加" should {

    "必須項目を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("AddIssueType.xml", request)
        val result = client.addIssueType(123, "テスト", ColorType.Color7EA800)
        getActualRequest(request) must_== getExpectedRequest("AddIssueType.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.IssueType
  def assertResponse(issueType: IssueType) = {
    issueType.id must_== 5
    issueType.name must_== "テスト"
    issueType.color must_== ColorType.Color7EA800
  }
}