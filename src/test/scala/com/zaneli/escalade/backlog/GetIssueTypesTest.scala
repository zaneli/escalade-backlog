package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetIssueTypesTest extends Specification with Mockito with TestUtil {

  "プロジェクトの種別を取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetIssueTypes.xml", request)
        val result = client.getIssueTypes(1)
        getActualRequest(request) must_== getExpectedRequest("GetIssueTypes.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.IssueType
  def assertResponse(issueTypes: Array[IssueType]) = {
    issueTypes.size must_== 4;
    {
      val issueType = issueTypes(0)
      issueType.id must_== 5
      issueType.name must_== "バグ"
      issueType.color must_== ColorType.Color990000
    }
    {
      val issueType = issueTypes(1)
      issueType.id must_== 6
      issueType.name must_== "タスク"
      issueType.color must_== ColorType.Color7EA800
    }
    {
      val issueType = issueTypes(2)
      issueType.id must_== 7
      issueType.name must_== "要望"
      issueType.color must_== ColorType.ColorFF9200
    }
    {
      val issueType = issueTypes(3)
      issueType.id must_== 8
      issueType.name must_== "その他"
      issueType.color must_== ColorType.Color2779CA
    }
  }
}