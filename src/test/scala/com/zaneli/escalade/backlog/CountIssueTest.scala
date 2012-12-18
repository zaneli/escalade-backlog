package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.{ FileType, PriorityType, ResolutionType, StatusType }
import com.zaneli.escalade.backlog.model.request.CountIssueParamBuilder
import com.zaneli.escalade.backlog.model.request.IssueConditionParamBuilder.CustomFieldParam

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CountIssueTest extends Specification with Mockito with TestUtil {

  "指定した条件に該当する課題件数を取得" should {

    "プロジェクトIDのみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("CountIssue.xml", request)
        val result = client.countIssue(CountIssueParamBuilder(5).build)
        getActualRequest(request) must_== getExpectedRequest("CountIssueByProjectId.xml")
        result must_== 64
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("CountIssue.xml", request)
        val param = CountIssueParamBuilder(5)
          .issueTypeId(1).componentId(2).versionId(3).milestoneId(4)
          .status(StatusType.NotSupported).priority(PriorityType.High)
          .assignerId(5).createdUserId(6).resolution(ResolutionType.NotSetting)
          .createdOnMin(getDate("20100101")).createdOnMax(getDate("20121201"))
          .updatedOnMin(getDate("20100131")).updatedOnMax(getDate("20121231"))
          .query("検索文字列").file(FileType.AttachedFile).customFields(CustomFieldParam(1, "a")).build
        val result = client.countIssue(param)
        getActualRequest(request) must_== getExpectedRequest("CountIssueByAllConditions.xml")
        result must_== 64
      }
    }

    "全項目複数指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("CountIssue.xml", request)
        val param = CountIssueParamBuilder(5)
          .issueTypeId(1, 2, 3).componentId(2, 3, 4).versionId(3, 4, 5).milestoneId(4, 5, 6)
          .status(StatusType.Processing, StatusType.Processed).priority(PriorityType.Middle, PriorityType.Low)
          .assignerId(5, 6, 7).createdUserId(6, 7, 8)
          .resolution(ResolutionType.Duplicated, ResolutionType.Invalid)
          .createdOnMin(getDate("20100101")).createdOnMax(getDate("20121201"))
          .updatedOnMin(getDate("20100131")).updatedOnMax(getDate("20121231"))
          .query("検索文字列").file(FileType.AttachedFile, FileType.SharedFileLink)
          .customFields(CustomFieldParam(1, 0D, 10D), CustomFieldParam(2, 100D, 200D), CustomFieldParam(3, 1.5, 3.5)).build
        val result = client.countIssue(param)
        getActualRequest(request) must_== getExpectedRequest("CountIssueByMultiConditions.xml")
        result must_== 64
      }
    }
  }
}