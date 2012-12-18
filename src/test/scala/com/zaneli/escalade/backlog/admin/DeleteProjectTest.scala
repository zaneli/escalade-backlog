package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.TestUtil

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class DeleteProjectTest extends Specification with Mockito with TestUtil {

  "プロジェクトを削除" should {

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getAdminClient("DeleteProject.xml", request)
        val result = client.deleteProject(2)
        getActualRequest(request) must_== getExpectedRequest("DeleteProject.xml")
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