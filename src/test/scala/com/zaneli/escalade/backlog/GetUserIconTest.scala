package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetUserIconTest extends Specification with Mockito with TestUtil {

  "ユーザのアイコン画像を取得" should {

    "ユーザIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetUserIcon.xml", request)
        val result = client.getUserIcon(123)
        getActualRequest(request) must_== getExpectedRequest("GetUserIconByUserId.xml")
        assertResponse(result)
      }
    }

    "ログインIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetUserIcon.xml", request)
        val result = client.getUserIcon("yamamoto")
        getActualRequest(request) must_== getExpectedRequest("GetUserIconByLoginId.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.UserIcon
  def assertResponse(userIcon: UserIcon) = {
    userIcon.id must_== 331
    userIcon.contentType must_== "image/gif"
    userIcon.data must_== getImgFileData
    userIcon.updatedOn.get must_== getDate("20101020144113")
  }
}