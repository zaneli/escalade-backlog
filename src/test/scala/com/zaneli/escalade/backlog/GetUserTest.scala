package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetUserTest extends Specification with Mockito with TestUtil {

  "ユーザを取得" should {

    "ユーザIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetUser.xml", request)
        val result = client.getUser(123)
        getActualRequest(request) must_== getExpectedRequest("GetUserByUserId.xml")
        assertResponse(result)
      }
    }

    "ログインIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetUser.xml", request)
        val result = client.getUser("yamamoto")
        getActualRequest(request) must_== getExpectedRequest("GetUserByLoginId.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.User
  def assertResponse(user: User) = {
    user.id must_== 123
    user.name must_== "やまもと@ヌーラボ"
    user.lang.get must_== "ja"
    user.updatedOn.get must_== getDate("20101020144113")
  }
}