package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.admin.model.RoleType

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeleteUserTest extends Specification with Mockito with TestUtil {

  "ユーザを削除" should {

    "IDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("DeleteUser.xml", request)
        val result = client.deleteUser(9876)
        getActualRequest(request) must_== getExpectedRequest("DeleteUser.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.User
  def assertResponse(user: User) = {
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