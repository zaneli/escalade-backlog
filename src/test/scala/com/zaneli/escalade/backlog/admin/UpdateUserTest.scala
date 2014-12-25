package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.admin.model.RoleType
import com.zaneli.escalade.backlog.admin.model.request.UpdateUserParamBuilder

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class UpdateUserTest extends Specification with Mockito with TestUtil {

  "ユーザを更新" should {

    "IDのみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateUser.xml", request)
        val result = client.updateUser(UpdateUserParamBuilder(9876).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateUserById.xml")
        assertResponse(result)
      }
    }

    "全項目指定, Backlog提供アイコン" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateUser.xml", request)
        val result = client.updateUser(
          UpdateUserParamBuilder(9876).password("testPassword1").name("テスト名前1").mailAddress("test1@local")
            .role(RoleType.GuestViewer).mailSetting(false, false).icon("10_metabolic_syndrome_salaried_man").build)
        getActualRequest(request) must_== getExpectedRequest("UpdateUserByAllItemsBacklogIcon.xml")
        assertResponse(result)
      }
    }

    "全項目指定, オリジナル画像" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateUser.xml", request)
        val result = client.updateUser(
          UpdateUserParamBuilder(9876).password("testPassword2").name("テスト名前2").mailAddress("test2@local")
            .role(RoleType.Reporter).mailSetting(true, false).icon(getImgFileData).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateUserByAllItemsOriginalImg.xml")
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