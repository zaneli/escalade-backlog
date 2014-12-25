package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.ColorType

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class UpdateIssueTypeTest extends Specification with Mockito with TestUtil {

  "プロジェクトの課題種別を更新" should {

    "必須項目を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("UpdateIssueType.xml", request)
        val result = client.updateIssueType(123, "要望", ColorType.ColorFF9200)
        getActualRequest(request) must_== getExpectedRequest("UpdateIssueType.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.IssueType
  def assertResponse(issueType: IssueType) = {
    issueType.id must_== 123
    issueType.name must_== "要望"
    issueType.color must_== ColorType.ColorFF9200
  }
}