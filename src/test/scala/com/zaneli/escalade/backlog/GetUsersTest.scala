package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetUsersTest extends Specification with Mockito with TestUtil {

  "プロジェクトの参加メンバーを取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetUsers.xml", request)
        val result = client.getUsers(1)
        getActualRequest(request) must_== getExpectedRequest("GetUsers.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.User
  def assertResponse(users: Array[User]) = {
    users.size must_== 2;
    {
      val user = users(0)
      user.id must_== 1
      user.name must_== "admin"
    }
    {
      val user = users(1)
      user.id must_== 2
      user.name must_== "demo"
    }
  }
}