package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetCommentsTest extends Specification with Mockito with TestUtil {

  "課題のコメントを取得" should {

    "課題IDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetComments.xml", request)
        val result = client.getComments(73)
        getActualRequest(request) must_== getExpectedRequest("GetComments.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Comment
  def assertResponse(comments: Array[Comment]) = {
    comments.size must_== 2;
    {
      val comment = comments(0)
      comment.id must_== 114
      comment.content must_== "報告が上り始めたのは４月１５日以降のようです。"

      val createdUser = comment.createdUser
      createdUser.id must_== 2
      createdUser.name must_== "demo"

      comment.createdOn must_== getDate("20050603114529")
      comment.updatedOn must_== getDate("20050603114529")
    }
    {
      val comment = comments(1)
      comment.id must_== 157
      comment.content must_== "じっ、時代の流れです。\nそっとしておきましょう。"

      val createdUser = comment.createdUser
      createdUser.id must_== 2
      createdUser.name must_== "demo"

      comment.createdOn must_== getDate("20050606115644")
      comment.updatedOn must_== getDate("20050606115644")
    }
  }
}