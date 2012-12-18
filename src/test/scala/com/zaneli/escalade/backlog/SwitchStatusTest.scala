package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.{ ResolutionType, StatusType }
import com.zaneli.escalade.backlog.model.request.SwitchStatusParamBuilder

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SwitchStatusTest extends Specification with Mockito with TestUtil {

  "課題の状態を変更" should {

    "課題キーと状態のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("SwitchStatus.xml", request)
        val result = client.switchStatus(SwitchStatusParamBuilder("DORA-310", StatusType.Processing).build)
        getActualRequest(request) must_== getExpectedRequest("SwitchStatusByKeyStatus.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("SwitchStatus.xml", request)
        val result = client.switchStatus(SwitchStatusParamBuilder("DORA-310", StatusType.Processed)
            .assignerId(10).resolution(ResolutionType.Corresponded).comment("テストコメント").build)
        getActualRequest(request) must_== getExpectedRequest("SwitchStatusByAllContents.xml")
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
    status.id must_== 2
    status.name must_== "処理中"

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
    issue.updatedOn.get must_== getDate("20081106135235")
  }
}