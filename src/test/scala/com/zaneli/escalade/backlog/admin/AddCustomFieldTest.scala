package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.model.CustomFieldType
import com.zaneli.escalade.backlog.admin.model.request.AddCustomFieldParamBuilder

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AddCustomFieldTest extends Specification with Mockito with TestUtil {

  "カスタム属性を作成" should {

    "必須項目のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddCustomField.xml", request)
        val result = client.addCustomField(AddCustomFieldParamBuilder(1, CustomFieldType.String, "文字列").build)
        getActualRequest(request) must_== getExpectedRequest("AddCustomFieldByRequiredItems.xml")
        assertResponse(result)
      }
    }

    "カスタム属性形式が数値" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddCustomField.xml", request)
        val result = client.addCustomField(AddCustomFieldParamBuilder(2, CustomFieldType.Number, "数値", 1, 2, 3)
          .required(true).description("説明").numField(-100.5, 100.5, 0, "%").build)
        getActualRequest(request) must_== getExpectedRequest("AddCustomFieldByNumberType.xml")
        assertResponse(result)
      }
    }

    "カスタム属性形式が日付" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddCustomField.xml", request)
        val result = client.addCustomField(AddCustomFieldParamBuilder(3, CustomFieldType.Date, "日付", 1)
          .required(false).description("説明").dateField(10, getDate("20120101"), getDate("20121231")).build)
        getActualRequest(request) must_== getExpectedRequest("AddCustomFieldByDateType.xml")
        assertResponse(result)
      }
    }

    "カスタム属性形式が複数リスト" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddCustomField.xml", request)
        val result = client.addCustomField(AddCustomFieldParamBuilder(4, CustomFieldType.Lists, "複数リスト", 5, 10, 15)
          .listField("項目1", "項目2").build)
        getActualRequest(request) must_== getExpectedRequest("AddCustomFieldByListsType.xml")
        assertResponse(result)
      }
    }

    "カスタム属性形式がチェックボックス" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddCustomField.xml", request)
        val result = client.addCustomField(AddCustomFieldParamBuilder(5, CustomFieldType.CheckBox, "チェックボックス", 1)
          .selectionField(false, "項目1", "項目2").build)
        getActualRequest(request) must_== getExpectedRequest("AddCustomFieldByCheckboxType.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.CustomFieldInfo
  def assertResponse(customField: CustomFieldInfo) = {
    customField.id must_== 12
    customField.customType must_== CustomFieldType.List
    customField.name must_== "OS"
    customField.description.get must beEmpty
    customField.required must beTrue

    val issueTypes = customField.issueTypes
    issueTypes.size must_== 1;
    {
      val issueType = issueTypes(0)
      issueType.id must_== 1
      issueType.name must_== "バグ"
      issueType.color must_== ColorType.Color990000
    }

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
    done
  }
}