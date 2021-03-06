package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetProjectsTest extends Specification with Mockito with TestUtil {

  "スペース内の全プロジェクトを取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("GetProjects.xml", request)
        val result = client.getProjects
        getActualRequest(request) must_== getExpectedRequest("GetProjects.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.admin.model.response.Project
  def assertResponse(projects: Array[Project]) = {
    projects.size must_== 1;
    {
      val project = projects(0)
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
}