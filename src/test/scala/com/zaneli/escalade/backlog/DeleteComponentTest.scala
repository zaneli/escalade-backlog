package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class DeleteComponentTest extends Specification with Mockito with TestUtil {

  "プロジェクトのカテゴリを削除" should {

    "カテゴリIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("DeleteComponent.xml", request)
        val result = client.deleteComponent(12)
        getActualRequest(request) must_== getExpectedRequest("DeleteComponent.xml")
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