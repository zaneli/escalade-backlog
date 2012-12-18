package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UpdateProjectUsersTest extends Specification with Mockito with TestUtil {

  "プロジェクト参加ユーザを一括更新" should {

    "プロジェクトIDとユーザIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateProjectUsers.xml", request)
        val result = client.updateProjectUsers(12, 9876, 9877)
        getActualRequest(request) must_== getExpectedRequest("UpdateProjectUsers.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.ProjectUser
  def assertResponse(projectUsers: Array[ProjectUser]) = {
    projectUsers.size must_== 4;
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
    {
      val projectUser = projectUsers(2)
      projectUser.id must_== 9878
      projectUser.userId must_== "takada"
      projectUser.name must_== "たかだ"
    }
    {
      val projectUser = projectUsers(3)
      projectUser.id must_== 9879
      projectUser.userId must_== "agata"
      projectUser.name must_== "あがた"
    }
  }
}