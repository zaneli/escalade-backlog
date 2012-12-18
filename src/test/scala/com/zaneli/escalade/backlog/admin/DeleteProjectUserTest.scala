package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeleteProjectUserTest extends Specification with Mockito with TestUtil {

  "プロジェクト参加ユーザを不参加にする" should {

    "プロジェクトIDとユーザIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("DeleteProjectUser.xml", request)
        val result = client.deleteProjectUser(12, 9876)
        getActualRequest(request) must_== getExpectedRequest("DeleteProjectUser.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.ProjectUser
  def assertResponse(projectUsers: Array[ProjectUser]) = {
    projectUsers.size must_== 3;
    {
      val projectUser = projectUsers(0)
      projectUser.id must_== 9877
      projectUser.userId must_== "yamamoto"
      projectUser.name must_== "やまもと"
    }
    {
      val projectUser = projectUsers(1)
      projectUser.id must_== 9878
      projectUser.userId must_== "takada"
      projectUser.name must_== "たかだ"
    }
    {
      val projectUser = projectUsers(2)
      projectUser.id must_== 9879
      projectUser.userId must_== "agata"
      projectUser.name must_== "あがた"
    }
  }
}