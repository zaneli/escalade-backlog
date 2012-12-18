package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetUsersTest extends Specification with Mockito with TestUtil {

  "スペース内の全ユーザを取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("GetUsers.xml", request)
        val result = client.getUsers
        getActualRequest(request) must_== getExpectedRequest("GetUsers.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.RoleType
  import com.zaneli.escalade.backlog.admin.model.response.User
  def assertResponse(users: Array[User]) = {
    users.size must_== 1;
    {
      val user = users(0)
      user.id must_== 9876
      user.userId must_== "yamamoto"
      user.name must_== "やまもと"
      user.mailAddress.get must_== "support@backlog.jp"
      user.role.get must_== RoleType.NormalUser

      val mailSetting = user.mailSetting.get
      mailSetting.mail must beTrue
      mailSetting.comment must beTrue

      user.createdOn.get must_== getDate("20090731151859")
      user.updatedOn.get must_== getDate("20100102151848")
    }
  }
}