package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.junit.runner.RunWith
import org.specs2.mock.Mockito
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GetResolutionsTest extends Specification with Mockito with TestUtil {

  "課題の状態一覧を取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetResolutions.xml", request)
        val result = client.getResolutions
        getActualRequest(request) must_== getExpectedRequest("GetResolutions.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Resolution
  def assertResponse(resolutions: Array[Resolution]) = {
    resolutions.size must_== 5;
    {
      val resolution = resolutions(0)
      resolution.id must_== 0
      resolution.name must_== "対応済み"
    }
    {
      val resolution = resolutions(1)
      resolution.id must_== 1
      resolution.name must_== "対応しない"
    }
    {
      val resolution = resolutions(2)
      resolution.id must_== 2
      resolution.name must_== "無効"
    }
    {
      val resolution = resolutions(3)
      resolution.id must_== 3
      resolution.name must_== "重複"
    }
    {
      val resolution = resolutions(4)
      resolution.id must_== 4
      resolution.name must_== "再現しない"
    }
  }
}