package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetIssueTest extends Specification with Mockito with TestUtil {

  "種別を取得" should {

    "課題キーを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetIssue.xml", request)
        val result = client.getIssue("DORA-15")
        getActualRequest(request) must_== getExpectedRequest("GetIssueByIssueKey.xml")
        assertResponse(result)
      }
    }

    "課題IDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetIssue.xml", request)
        val result = client.getIssue(73)
        getActualRequest(request) must_== getExpectedRequest("GetIssueByIssueId.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.ColorType
  import com.zaneli.escalade.backlog.model.response.Issue
  def assertResponse(issue: Issue) = {
    issue.id must_== 73
    issue.key must_== "DORA-15"
    issue.summary must_== "発声器官に不具合？"
    issue.description must_== "初期出荷分の一部に発声器官に異常が発生しているとの報告がありました。"
    issue.url.get must_== "https://demo.backlog.jp/view/DORA-15"
    issue.dueDate must beNone
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
    status.id must_== 4
    status.name must_== "完了"

    issue.components must beEmpty

    val versions = issue.versions
    versions.size must_== 1;
    {
      val version = versions(0)
      version.id must_== 6
      version.name must_== "初期出荷"
    }

    issue.milestones must beEmpty

    val createdUser = issue.createdUser.get
    createdUser.id must_== 2
    createdUser.name must_== "demo"

    val assigner = issue.assigner.get
    assigner.id must_== 2
    assigner.name must_== "demo"

    issue.createdOn.get must_== getDate("20050603114444")
    issue.updatedOn.get must_== getDate("20060719150533")

    issue.customFields must beEmpty
  }
}