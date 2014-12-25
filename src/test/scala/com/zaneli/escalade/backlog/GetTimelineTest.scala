package com.zaneli.escalade.backlog

import java.io.ByteArrayOutputStream

import org.specs2.mock.Mockito
import org.specs2.mutable._

class GetTimelineTest extends Specification with Mockito with TestUtil {

  "参加プロジェクトすべての課題の更新情報を取得" should {

    "指定パラメータ無し" in {
      using(new ByteArrayOutputStream()) { request =>
        val client = getClient("GetTimeline.xml", request)
        val result = client.getTimeline
        getActualRequest(request) must_== getExpectedRequest("GetTimeline.xml")
        assertResponse(result)
      }
    }
  }

  import com.zaneli.escalade.backlog.model.response.Timeline
  def assertResponse(timelines: Array[Timeline]) = {
    timelines.size must_== 3;
    {
      val timeline = timelines(0)

      val activityType = timeline.activityType
      activityType.id must_== 3
      activityType.name must_== "コメント"

      timeline.content must_== "いえいえ、気にしないでください。\nこちらこそ気をつけます。"
      timeline.updatedOn must_== getDate("20101020161418")

      val user = timeline.user
      user.id must_== 3
      user.name must_== "Agata"

      val issue = timeline.issue
      issue.id must_== 10294
      issue.key must_== "BLGWEBSITE-2361"
      issue.summary must_== "発声器官に不具合？"
      issue.description must_== "初期出荷分の一部に発声器官に異常が発生しているとの報告がありました。"

      val priority = issue.priority.get
      priority.id must_== 4
      priority.name must_== "低"
    }
    {
      val timeline = timelines(1)

      val activityType = timeline.activityType
      activityType.id must_== 3
      activityType.name must_== "コメント"

      timeline.content must_== "どのような異常が発生しているのか確認していただけますか？"
      timeline.updatedOn must_== getDate("20101020161255")

      val user = timeline.user
      user.id must_== 3
      user.name must_== "Agata"

      val issue = timeline.issue
      issue.id must_== 1029829
      issue.key must_== "BLG-2171"
      issue.summary must_== "BacklogAPIで課題を登録してみる"
      issue.description must_== "お試しで登録してみたいと思います。"

      val priority = issue.priority.get
      priority.id must_== 3
      priority.name must_== "中"
    }
    {
      val timeline = timelines(2)

      val activityType = timeline.activityType
      activityType.id must_== 3
      activityType.name must_== "コメント"

      timeline.content must_== "確認しました。\nありがとうございます〜。"
      timeline.updatedOn must_== getDate("20101020155136")

      val user = timeline.user
      user.id must_== 3982
      user.name must_== "田中"

      val issue = timeline.issue
      issue.id must_== 109822
      issue.key must_== "EXAMPLE-384"
      issue.summary must_== "請求書の再発行"
      issue.description must_== "海山商事さまより請求書の再発行依頼がありましたので、対応お願いします。"

      val priority = issue.priority.get
      priority.id must_== 3
      priority.name must_== "中"
    }
  }
}