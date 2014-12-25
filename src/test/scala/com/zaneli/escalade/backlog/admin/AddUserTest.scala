package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.admin.model.RoleType
import com.zaneli.escalade.backlog.admin.model.request.AddUserParamBuilder

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class AddUserTest extends Specification with Mockito with TestUtil {

  "ユーザを追加" should {

    "必須項目のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddUser.xml", request)
        val result = client.addUser(
          AddUserParamBuilder("yamamoto", "password", "やまもと", "support@backlog.jp", RoleType.NormalUser).build)
        getActualRequest(request) must_== getExpectedRequest("AddUserByRequiredItems.xml")
        assertResponse(result)
      }
    }

    "全項目指定, Backlog提供アイコン" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddUser.xml", request)
        val result = client.addUser(
          AddUserParamBuilder("yamamoto", "password", "やまもと", "support@backlog.jp", RoleType.NormalUser)
            .mailSetting(true, true).icon("05_male_designer").build)
        getActualRequest(request) must_== getExpectedRequest("AddUserByAllItemsBacklogIcon.xml")
        assertResponse(result)
      }
    }

    "全項目指定, オリジナル画像" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddUser.xml", request)
        val result = client.addUser(
          AddUserParamBuilder("yamamoto", "admin", "やまもと", "support@backlog.jp", RoleType.Admin)
            .mailSetting(false, false).icon(getImgFileData).build)
        getActualRequest(request) must_== getExpectedRequest("AddUserByAllItemsOriginalImg.xml")
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