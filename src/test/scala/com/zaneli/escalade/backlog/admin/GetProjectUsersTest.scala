package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetProjectUsersTest extends Specification with Mockito with TestUtil {

  "プロジェクト参加ユーザリストを取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("GetProjectUsers.xml", request)
        val result = client.getProjectUsers(1)
        getActualRequest(request) must_== getExpectedRequest("GetProjectUsers.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.ProjectUser
  def assertResponse(projectUsers: Array[ProjectUser]) = {
    projectUsers.size must_== 2;
    {
      val projectUser = projectUsers(0)
      projectUser.id must_== 9876
      projectUser.userId must_== "admin"
      projectUser.name must_== "管理者"
    }
    {
      val projectUser = projectUsers(1)
      projectUser.id must_== 9877
      projectUser.userId must_== "yamamoto"
      projectUser.name must_== "やまもと"
    }
  }
}