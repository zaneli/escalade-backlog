package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetComponentsTest extends Specification with Mockito with TestUtil {

  "プロジェクトのカテゴリを取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetComponents.xml", request)
        val result = client.getComponents(1)
        getActualRequest(request) must_== getExpectedRequest("GetComponents.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Component
  def assertResponse(components: Array[Component]) = {
    components.size must_== 5;
    {
      val component = components(0)
      component.id must_== 1967
      component.name must_== "要件定義"
    }
    {
      val component = components(1)
      component.id must_== 3
      component.name must_== "試作"
    }
    {
      val component = components(2)
      component.id must_== 2
      component.name must_== "プロモーション"
    }
    {
      val component = components(3)
      component.id must_== 3673
      component.name must_== "デザイン"
    }
    {
      val component = components(4)
      component.id must_== 6192
      component.name must_== "セキュリティ"
    }
  }
}