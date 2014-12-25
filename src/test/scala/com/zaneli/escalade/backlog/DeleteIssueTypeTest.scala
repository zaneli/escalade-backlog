package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.ColorType

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class DeleteIssueTypeTest extends Specification with Mockito with TestUtil {

  "プロジェクトの課題種別を削除" should {

    "種別IDのみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("DeleteIssueType.xml", request)
        val result = client.deleteIssueType(123)
        getActualRequest(request) must_== getExpectedRequest("DeleteIssueTypeById.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("DeleteIssueType.xml", request)
        val result = client.deleteIssueType(123, Some(456))
        getActualRequest(request) must_== getExpectedRequest("DeleteIssueTypeByAllContents.xml")
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