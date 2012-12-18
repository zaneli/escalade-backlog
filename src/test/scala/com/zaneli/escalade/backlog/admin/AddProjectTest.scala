package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil
import com.zaneli.escalade.backlog.admin.model.request.AddProjectParamBuilder

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AddProjectTest extends Specification with Mockito with TestUtil {

  "プロジェクトを追加" should {

    "必須項目のみ指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddProject.xml", request)
        val result = client.addProject(AddProjectParamBuilder("新規開発プロジェクト", "NEWP").build)
        getActualRequest(request) must_== getExpectedRequest("AddProjectByRequiredItems.xml")
        assertResponse(result)
      }
    }

    "全項目指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("AddProject.xml", request)
        val result = client.addProject(AddProjectParamBuilder("新規開発プロジェクト", "NEWP").useChart(true).build)
        getActualRequest(request) must_== getExpectedRequest("AddProjectByAllItems.xml")
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