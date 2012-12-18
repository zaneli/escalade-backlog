package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetProjectsTest extends Specification with Mockito with TestUtil {

  "参加プロジェクトを取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetProjects.xml", request)
        val result = client.getProjects
        getActualRequest(request) must_== getExpectedRequest("GetProjects.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Project
  def assertResponse(projects: Array[Project]) = {
    projects.size must_== 2;
    {
      val project = projects(0)
      project.id must_== 2
      project.name must_== "StruWork"
      project.key must_== "STWK"
      project.url must_== "https://demo.backlog.jp/projects/STWK"
      project.archived must beTrue
    }
    {
      val project = projects(1)
      project.id must_== 1
      project.name must_== "ネコ型ロボット製造計画"
      project.key must_== "DORA"
      project.url must_== "https://demo.backlog.jp/projects/DORA"
      project.archived must beFalse
    }
  }
}