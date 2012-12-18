package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetProjectTest extends Specification with Mockito with TestUtil {

  "プロジェクトを取得" should {

    "プロジェクトキーを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetProject.xml", request)
        val result = client.getProject("DORA")
        getActualRequest(request) must_== getExpectedRequest("GetProjectByProjectKey.xml")
        assertResponse(result)
      }
    }

    "プロジェクトIDを指定" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetProject.xml", request)
        val result = client.getProject(1)
        getActualRequest(request) must_== getExpectedRequest("GetProjectByProjectId.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Project
  def assertResponse(project: Project) = {
    project.id must_== 1
    project.name must_== "ネコ型ロボット製造計画"
    project.key must_== "DORA"
    project.url must_== "https://demo.backlog.jp/projects/DORA"
    project.archived must beFalse
  }
}