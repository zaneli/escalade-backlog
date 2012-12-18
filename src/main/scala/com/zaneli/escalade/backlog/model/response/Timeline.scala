package com.zaneli.escalade.backlog.model.response

case class Timeline private (
  activityType: ActivityType, content: String, updatedOn: java.util.Date, user: User, issue: Issue) extends ResponseModel {

}

object Timeline {
  private def apply(map: Map[String, Any]): Timeline = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModel, getDateValue, getStringValue }
    val activityType = createModel(map, "type", classOf[ActivityType])
    val content = getStringValue(map, "content")
    val updatedOn = getDateValue(map, "updated_on")
    val user = createModel(map, "user", classOf[User])
    val issue = createModel(map, "issue", classOf[Issue])
    new Timeline(activityType.get, content.get, updatedOn.get, user.get, issue.get)
  }
}