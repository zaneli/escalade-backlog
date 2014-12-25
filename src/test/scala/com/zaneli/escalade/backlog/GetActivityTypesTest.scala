package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetActivityTypesTest extends Specification with Mockito with TestUtil {

  "課題の更新情報の種別一覧を取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetActivityTypes.xml", request)
        val result = client.getActivityTypes
        getActualRequest(request) must_== getExpectedRequest("GetActivityTypes.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.ActivityType
  def assertResponse(activityTypes: Array[ActivityType]) = {
    activityTypes.size must_== 3;
    {
      val activityType = activityTypes(0)
      activityType.id must_== 1
      activityType.name must_== "課題"
    }
    {
      val activityType = activityTypes(1)
      activityType.id must_== 2
      activityType.name must_== "更新"
    }
    {
      val activityType = activityTypes(2)
      activityType.id must_== 3
      activityType.name must_== "コメント"
    }
  }
}