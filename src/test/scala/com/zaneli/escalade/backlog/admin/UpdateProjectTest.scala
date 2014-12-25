package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.admin.model.request.UpdateProjectParamBuilder

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class UpdateProjectTest extends Specification with Mockito with TestUtil {

  "プロジェクトを更新" should {

    "必須項目のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateProject.xml", request)
        val result = client.updateProject(UpdateProjectParamBuilder(2).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateProjectByRequiredItems.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("UpdateProject.xml", request)
        val result = client.updateProject(UpdateProjectParamBuilder(2)
            .name("新規開発プロジェクト").key("STWK").useChart(false).archived(true).build)
        getActualRequest(request) must_== getExpectedRequest("UpdateProjectByAllItems.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.Project
  def assertResponse(project: Project) = {
    project.id must_== 2
    project.name must_== "新規開発プロジェクト"
    project.key must_== "STWK"
    project.url must_== "https://demo.backlog.jp/projects/STWK"
    project.useChart.get must beTrue
    project.archived must beFalse
    project.createdOn.get must_== getDate("20090731151859")
    project.updatedOn.get must_== getDate("20100102151848")
  }
}