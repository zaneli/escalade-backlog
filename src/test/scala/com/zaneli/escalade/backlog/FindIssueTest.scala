package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.{ FileType, PriorityType, ResolutionType, StatusType }
import com.zaneli.escalade.backlog.model.request.FindIssueParamBuilder
import com.zaneli.escalade.backlog.model.request.IssueConditionParamBuilder.CustomFieldParam

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class FindIssueTest extends Specification with Mockito with TestUtil {

  "指定した条件に該当する課題件数を取得" should {

    "プロジェクトIDのみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("FindIssue.xml", request)
        val result = client.findIssue(FindIssueParamBuilder(5).build)
        getActualRequest(request) must_== getExpectedRequest("FindIssueByProjectId.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("FindIssue.xml", request)
        val param = FindIssueParamBuilder(5)
          .issueTypeId(1).componentId(2).versionId(3).milestoneId(4)
          .status(StatusType.NotSupported).priority(PriorityType.High)
          .assignerId(5).createdUserId(6).resolution(ResolutionType.NotSetting)
          .createdOnMin(getDate("20100101")).createdOnMax(getDate("20121201"))
          .updatedOnMin(getDate("20100131")).updatedOnMax(getDate("20121231"))
          .query("検索文字列").sort("ISSUE_TYPE").order(true).offset(0).limit(50)
          .file(FileType.AttachedFile).customFields(CustomFieldParam(1, "a")).build
        val result = client.findIssue(param)
        getActualRequest(request) must_== getExpectedRequest("FindIssueByAllConditions.xml")
        assertResponse(result)
      }
    }

    "全項目複数指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("FindIssue.xml", request)
        val param = FindIssueParamBuilder(5)
          .issueTypeId(1, 2, 3).componentId(2, 3, 4).versionId(3, 4, 5).milestoneId(4, 5, 6)
          .status(StatusType.Processing, StatusType.Processed).priority(PriorityType.Middle, PriorityType.Low)
          .assignerId(5, 6, 7).createdUserId(6, 7, 8)
          .resolution(ResolutionType.Duplicated, ResolutionType.Invalid)
          .createdOnMin(getDate("20100101")).createdOnMax(getDate("20121201"))
          .updatedOnMin(getDate("20100131")).updatedOnMax(getDate("20121231"))
          .query("検索文字列").sort("SUMMARY").order(false).offset(50).limit(100)
          .file(FileType.AttachedFile, FileType.SharedFileLink)
          .customFields(CustomFieldParam(1, 0D, 10D), CustomFieldParam(2, 100D, 200D), CustomFieldParam(3, 1.5, 3.5)).build
        val result = client.findIssue(param)
        getActualRequest(request) must_== getExpectedRequest("FindIssueByMultiConditions.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.Issue
  def assertResponse(issues: Array[Issue]) = {
    issues.size must_== 2;
    {
      val issue = issues(0)
      issue.id must_== 250274
      issue.key must_== "DORA-310"
      issue.summary must_== "BacklogAPIで課題を登録してみる(1225945534)"
      issue.description must_== "XML-RPCで課題を登録してみるテストです。\nいわゆるBacklogAPIです。\n\n詳細を追記します。"
      issue.url.get must_== "https://demo.backlog.jp/view/DORA-310"
      issue.dueDate.get must_== getDate("20081230000000")
      issue.startDate must beNone
      issue.estimatedHours must beNone
      issue.actualHours must beNone

      val issueType = issue.issueType.get
      issueType.id must_== 5
      issueType.name must_== "バグ"
      issueType.color must_== ColorType.Color990000

      val priority = issue.priority.get
      priority.id must_== 2
      priority.name must_== "高"

      val resolution = issue.resolution.get
      resolution.id must_== 0
      resolution.name must_== "対応済み"

      val status = issue.status.get
      status.id must_== 1
      status.name must_== "未対応"

      issue.components must beEmpty

      issue.versions must beEmpty

      val milestones = issue.milestones
      milestones.size must_== 1;
      {
        val milestone = milestones(0)
        milestone.id must_== 5
        milestone.name must_== "お披露目パーティ"
        milestone.date.get must_== getDate("21121010")
      }

      val createUser = issue.createdUser.get
      createUser.id must_== 2
      createUser.name must_== "demo"

      val assigner = issue.assigner.get
      assigner.id must_== 2
      assigner.name must_== "demo"

      issue.createdOn.get must_== getDate("20081106132534")
      issue.updatedOn.get must_== getDate("20081106135201")
    }
    {
      val issue = issues(1)
      issue.id must_== 250283
      issue.key must_== "DORA-312"
      issue.summary must_== "BacklogAPIで課題を登録してみる(1225946062)"
      issue.description must_== "XML-RPCで課題を登録してみるテストです。\nいわゆるBacklogAPIです。"
      issue.url.get must_== "https://demo.backlog.jp/view/DORA-312"
      issue.dueDate.get must_== getDate("20081230000000")
      issue.startDate must beNone
      issue.estimatedHours must beNone
      issue.actualHours must beNone

      val issueType = issue.issueType.get
      issueType.id must_== 5
      issueType.name must_== "バグ"
      issueType.color must_== ColorType.Color990000

      val priority = issue.priority.get
      priority.id must_== 2
      priority.name must_== "高"

      issue.resolution must beNone

      val status = issue.status.get
      status.id must_== 1
      status.name must_== "未対応"

      issue.components must beEmpty

      issue.versions must beEmpty

      val milestones = issue.milestones
      milestones.size must_== 1;
      {
        val milestone = milestones(0)
        milestone.id must_== 5
        milestone.name must_== "お披露目パーティ"
        milestone.date.get must_== getDate("21121010")
      }

      val createUser = issue.createdUser.get
      createUser.id must_== 2
      createUser.name must_== "demo"

      issue.assigner must beNone

      issue.createdOn.get must_== getDate("20081106133423")
      issue.updatedOn.get must_== getDate("20081106133423")
    }
  }
}