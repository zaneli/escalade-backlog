package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class UpdateComponentTest extends Specification with Mockito with TestUtil {

  "プロジェクトのカテゴリを更新" should {

    "カテゴリIDとカテゴリ名を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("UpdateComponent.xml", request)
        val result = client.updateComponent(12, "打合せ関連")
        getActualRequest(request) must_== getExpectedRequest("UpdateComponent.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Component
  def assertResponse(component: Component) = {
    component.id must_== 12345
    component.name must_== "打合せ関連"
  }
}