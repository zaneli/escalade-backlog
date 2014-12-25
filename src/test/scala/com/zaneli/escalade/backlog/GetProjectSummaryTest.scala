package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetProjectSummaryTest extends Specification with Mockito with TestUtil {

  "プロジェクト状況を取得" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetProjectSummary.xml", request)
        val result = client.getProjectSummary(123)
        getActualRequest(request) must_== getExpectedRequest("GetProjectSummary.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.ProjectSummary
  def assertResponse(projectSummary: ProjectSummary) = {
    projectSummary.id must_== 123
    projectSummary.name must_== "バックログ"
    projectSummary.key must_== "BLG"
    projectSummary.url must_== "https://demo.backlog.jp/projects/BLG"

    val statuses = projectSummary.statuses
    statuses.size must_== 4;
    {
      val status = statuses(0)
      status.id must_== 1
      status.name must_== "未対応"
      status.count.get must_== 267
    }
    {
      val status = statuses(1)
      status.id must_== 2
      status.name must_== "処理中"
      status.count.get must_== 28
    }
    {
      val status = statuses(2)
      status.id must_== 3
      status.name must_== "処理済み"
      status.count.get must_== 16
    }
    {
      val status = statuses(3)
      status.id must_== 4
      status.name must_== "完了"
      status.count.get must_== 1861
    }

    val milestones = projectSummary.milestones
    milestones.size must_== 2;
    {
      val milestone = milestones(0)
      milestone.id must_== 1074
      milestone.name must_== "R2010-10-28 改善リリース"
      milestone.dueDate.get must_== getDate("20101028")
      val statuses = milestone.statuses
      statuses.size must_== 4;
      {
        val status = statuses(0)
        status.id must_== 1
        status.name must_== "未対応"
        status.count.get must_== 3
      }
      {
        val status = statuses(1)
        status.id must_== 2
        status.name must_== "処理中"
        status.count.get must_== 3
      }
      {
        val status = statuses(2)
        status.id must_== 3
        status.name must_== "処理済み"
        status.count.get must_== 4
      }
      {
        val status = statuses(3)
        status.id must_== 4
        status.name must_== "完了"
        status.count.get must_== 0
      }
    }
    {
      val milestone = milestones(1)
      milestone.id must_== 1074
      milestone.name must_== "R2010-10-06 改善リリース"
      milestone.dueDate.get must_== getDate("20101006")
      val statuses = milestone.statuses
      statuses.size must_== 4;
      {
        val status = statuses(0)
        status.id must_== 1
        status.name must_== "未対応"
        status.count.get must_== 0
      }
      {
        val status = statuses(1)
        status.id must_== 2
        status.name must_== "処理中"
        status.count.get must_== 0
      }
      {
        val status = statuses(2)
        status.id must_== 3
        status.name must_== "処理済み"
        status.count.get must_== 0
      }
      {
        val status = statuses(3)
        status.id must_== 4
        status.name must_== "完了"
        status.count.get must_== 9
      }
    }

    val currentMilestone = projectSummary.currentMilestone.get
    currentMilestone.id must_== 49
    currentMilestone.name must_== "R2010-10-28"
    currentMilestone.dueDate.get must_== getDate("20101022")
    currentMilestone.burndownChart must_== getImgFileData
  }
}