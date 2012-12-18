package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.{ PriorityType, ResolutionType }
import com.zaneli.escalade.backlog.model.request.UpdateIssueParamBuilder
import com.zaneli.escalade.backlog.model.request.IssueContentParamBuilder.CustomFieldParam

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class UpdateIssueTest extends Specification with Mockito with TestUtil {

  "課題を更新" should {

    "課題キーのみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("UpdateIssue.xml", request)
        val result = client.updateIssue(UpdateIssueParamBuilder("DORA-310").build)
        getActualRequest(request) must_== getExpectedRequest("UpdateIssueByKey.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("UpdateIssue.xml", request)
        val result = client.updateIssue(UpdateIssueParamBuilder("DORA-310").description("テスト詳細")
          .startDate(getDate("20121010")).dueDate(getDate("20130101")).estimatedHours(10.5).actualHours(2.5)
          .issueTypeId(1).component("テストコンポーネント").versionId(1).milestone("テストマイルストーン").priority(PriorityType.High)
          .assignerId(10).resolution(ResolutionType.Corresponded).comment("テストコメント")
          .customFields(CustomFieldParam(1, "テストカスタム属性")).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateIssueByAllContents.xml")
        assertResponse(result)
      }
    }

    "全項目複数指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("UpdateIssue.xml", request)
        val result = client.updateIssue(UpdateIssueParamBuilder("DORA-310").description("テスト詳細")
          .startDate(getDate("20121015")).dueDate(getDate("20130105")).estimatedHours(10.5).actualHours(2.5)
          .issueTypeId(1).componentId(10, 20, 30).version("テストバージョン1", "テストバージョン2", "テストバージョン3")
          .milestoneId(1, 2, 3).priority(PriorityType.Low).assignerId(10)
          .resolution(ResolutionType.Invalid).comment("テストコメント")
          .customFields(CustomFieldParam(1, "任意の文字列1", 11, 12, 13), CustomFieldParam(2, "任意の文字列2", 21, 22, 23)).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateIssueByAllMultiContents.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.Issue
  def assertResponse(issue: Issue) = {
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

    val createdUser = issue.createdUser.get
    createdUser.id must_== 2
    createdUser.name must_== "demo"

    val assigner = issue.assigner.get
    assigner.id must_== 2
    assigner.name must_== "demo"

    issue.createdOn.get must_== getDate("20081106132534")
    issue.updatedOn.get must_== getDate("20081106135234")

    issue.customFields must beEmpty
  }
}