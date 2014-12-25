package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetStatusesTest extends Specification with Mockito with TestUtil {

  "課題の状態一覧を取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetStatuses.xml", request)
        val result = client.getStatuses
        getActualRequest(request) must_== getExpectedRequest("GetStatuses.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Status
  def assertResponse(statuses: Array[Status]) = {
    statuses.size must_== 4;
    {
      val status = statuses(0)
      status.id must_== 1
      status.name must_== "未対応"
    }
    {
      val status = statuses(1)
      status.id must_== 2
      status.name must_== "処理中"
    }
    {
      val status = statuses(2)
      status.id must_== 3
      status.name must_== "処理済み"
    }
    {
      val status = statuses(3)
      status.id must_== 4
      status.name must_== "完了"
    }
  }
}