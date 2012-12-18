package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetPrioritiesTest extends Specification with Mockito with TestUtil {

  "課題の優先度一覧を取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetPriorities.xml", request)
        val result = client.getPriorities
        getActualRequest(request) must_== getExpectedRequest("GetPriorities.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Priority
  def assertResponse(priorities: Array[Priority]) = {
    priorities.size must_== 3;
    {
      val priority = priorities(0)
      priority.id must_== 2
      priority.name must_== "高"
    }
    {
      val priority = priorities(1)
      priority.id must_== 3
      priority.name must_== "中"
    }
    {
      val priority = priorities(2)
      priority.id must_== 4
      priority.name must_== "低"
    }
  }
}