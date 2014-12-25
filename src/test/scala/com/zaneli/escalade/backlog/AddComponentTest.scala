package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class AddComponentTest extends Specification with Mockito with TestUtil {

  "プロジェクトのカテゴリを追加" should {

    "プロジェクトIDとカテゴリ名を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("AddComponent.xml", request)
        val result = client.addComponent(12, "打合せ関連")
        getActualRequest(request) must_== getExpectedRequest("AddComponent.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Component
  def assertResponse(component: Component) = {
    component.id must_== 1967
    component.name must_== "打合せ関連"
  }
}