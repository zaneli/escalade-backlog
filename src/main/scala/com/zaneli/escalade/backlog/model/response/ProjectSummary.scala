package com.zaneli.escalade.backlog.model.response

case class ProjectSummary private (
  id: Int,
  name: String,
  key: String,
  url: String,
  statuses: Array[Status],
  milestones: Array[Milestone],
  currentMilestone: Option[Milestone]) extends ResponseModel {

}

object ProjectSummary {
  private def apply(map: Map[String, Any]): ProjectSummary = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModel, createModels, getBooleanValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val key = getStringValue(map, "key")
    val url = getStringValue(map, "url")
    val statuses = createModels(map, "statuses", classOf[Status])
    val milestones = createModels(map, "milestones", classOf[Milestone])
    val currentMilestone = createModel(map, "current_milestone", classOf[Milestone])
    new ProjectSummary(id.get, name.get, key.get, url.get, statuses, milestones, currentMilestone)
  }
}