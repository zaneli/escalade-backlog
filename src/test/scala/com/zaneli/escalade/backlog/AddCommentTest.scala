package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class AddCommentTest extends Specification with Mockito with TestUtil {

  "課題にコメントを追加" should {

    "課題キーとコメントを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("AddComment.xml", request)
        val result = client.addComment("BLG-10", "テストコメント")
        getActualRequest(request) must_== getExpectedRequest("AddComment.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Comment
  def assertResponse(comment: Comment) = {
    comment.id must_== 32
    comment.content must_== "テストコメント"
    comment.createdOn must_== getDate("20101022153527")
    comment.updatedOn must_== getDate("20101022153527")

    val createUser = comment.createdUser
    createUser.id must_== 1
    createUser.name must_== "やまもと"
  }
}