package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.request.GetCustomFieldParamBuilder

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetCustomFieldsTest extends Specification with Mockito with TestUtil {

  "カスタム属性の情報を取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetCustomFields.xml", request)
        val result = client.getCustomFields(GetCustomFieldParamBuilder(123).build)
        getActualRequest(request) must_== getExpectedRequest("GetCustomFieldsByProjectId.xml")
        assertResponse(result)
      }
    }

    "プロジェクトIDと種別ID1つを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetCustomFields.xml", request)
        val result = client.getCustomFields(GetCustomFieldParamBuilder(123).issueTypeId(5).build)
        getActualRequest(request) must_== getExpectedRequest("GetCustomFieldsByProjectIdAndTypeId.xml")
        assertResponse(result)
      }
    }

    "プロジェクトIDと種別名1つを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetCustomFields.xml", request)
        val result = client.getCustomFields(GetCustomFieldParamBuilder(123).issueType("タスク").build)
        getActualRequest(request) must_== getExpectedRequest("GetCustomFieldsByProjectIdAndType.xml")
        assertResponse(result)
      }
    }

    "プロジェクトIDと種別ID複数を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetCustomFields.xml", request)
        val result = client.getCustomFields(GetCustomFieldParamBuilder(123).issueTypeId(5, 6, 7).build)
        getActualRequest(request) must_== getExpectedRequest("GetCustomFieldsByProjectIdAndTypeIds.xml")
        assertResponse(result)
      }
    }

    "プロジェクトIDと種別名複数を指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetCustomFields.xml", request)
        val result = client.getCustomFields(GetCustomFieldParamBuilder(123).issueType("タスク", "要望").build)
        getActualRequest(request) must_== getExpectedRequest("GetCustomFieldsByProjectIdAndTypes.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.CustomFieldType
  import com.zaneli.escalade.backlog.model.response.CustomFieldInfo
  def assertResponse(customFields: Array[CustomFieldInfo]) = {
    customFields.size must_== 1;
    {
      val customField = customFields(0)
      customField.id must_== 3
      customField.customType must_== CustomFieldType.RadioButton
      customField.name must_== "ブラウザ"
      customField.description.get must beEmpty
      customField.required must beFalse

      val issueTypes = customField.issueTypes
      issueTypes.size must_== 1;
      {
        val issueType = issueTypes(0)
        issueType.id must_== 1
        issueType.name must_== "バグ"
        issueType.color must_== ColorType.Color990000
      }

      customField match {
        case selectionField: CustomFieldInfo.SelectionInfo => {
          selectionField.allowInput must beTrue
          val items = selectionField.items
          items.size must_== 5;
          {
            val item = items(0)
            item.id must_== 1
            item.name must_== "IE"
          }
          {
            val item = items(1)
            item.id must_== 2
            item.name must_== "Firefox"
          }
          {
            val item = items(2)
            item.id must_== 3
            item.name must_== "Safari"
          }
          {
            val item = items(3)
            item.id must_== 4
            item.name must_== "Chrome"
          }
          {
            val item = items(4)
            item.id must_== 5
            item.name must_== "Opera"
          }
        }
        case _ => failure(customField.getClass.toString)
      }
    }
    done
  }
}