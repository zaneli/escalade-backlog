package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.model.CustomFieldType

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeleteCustomFieldTest extends Specification with Mockito with TestUtil {

  "カスタム属性を削除" should {

    "カスタム属性IDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("DeleteCustomField.xml", request)
        val result = client.deleteCustomField(18)
        getActualRequest(request) must_== getExpectedRequest("DeleteCustomField.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.CustomFieldInfo
  def assertResponse(customField: CustomFieldInfo) = {
    customField.id must_== 18
    customField.customType must_== CustomFieldType.List
    customField.name must_== "OS"
    customField.description.get must beEmpty
    customField.required must beTrue

    customField.issueTypes must beEmpty

    customField match {
      case listField: CustomFieldInfo.ListInfo => {
        val items = listField.items
        items.size must_== 3;
        {
          val item = items(0)
          item.id must_== 0
          item.name must_== "Windows"
        }
        {
          val item = items(1)
          item.id must_== 1
          item.name must_== "Mac"
        }
        {
          val item = items(2)
          item.id must_== 2
          item.name must_== "Linux"
        }
      }
      case _ => failure(customField.getClass.toString)
    }
  }
}