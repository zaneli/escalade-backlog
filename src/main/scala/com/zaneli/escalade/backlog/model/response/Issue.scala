package com.zaneli.escalade.backlog.model.response

case class Issue private (
  id: Int,
  key: String,
  summary: String,
  description: String,
  url: Option[String],
  dueDate: Option[java.util.Date],
  startDate: Option[java.util.Date],
  estimatedHours: Option[Double],
  actualHours: Option[Double],
  issueType: Option[IssueType],
  priority: Option[Priority],
  resolution: Option[Resolution],
  status: Option[Status],
  components: Array[Component],
  versions: Array[Version],
  milestones: Array[Milestone],
  createdUser: Option[User],
  assigner: Option[User],
  createdOn: Option[java.util.Date],
  updatedOn: Option[java.util.Date],
  customFields: Array[CustomFieldValue]) extends ResponseModel {

}

object Issue {
  private def apply(map: Map[String, Any]): Issue = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModel, createModels, getDateValue, getDoubleValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val key = getStringValue(map, "key")
    val summary = getStringValue(map, "summary")
    val description = getStringValue(map, "description")
    val url = getStringValue(map, "url")
    val dueDate = getDateValue(map, "due_date")
    val startDate = getDateValue(map, "start_date")
    val estimatedHours = getDoubleValue(map, "estimated_hours")
    val actualHours = getDoubleValue(map, "actualHours")
    val issueType = createModel(map, "issueType", classOf[IssueType])
    val priority = createModel(map, "priority", classOf[Priority])
    val resolution = createModel(map, "resolution", classOf[Resolution])
    val status = createModel(map, "status", classOf[Status])
    val components = createModels(map, "components", classOf[Component])
    val versions = createModels(map, "versions", classOf[Version])
    val milestones = createModels(map, "milestones", classOf[Milestone])
    val createdUser = createModel(map, "created_user", classOf[User])
    val assigner = createModel(map, "assigner", classOf[User])
    val createdOn = getDateValue(map, "created_on")
    val updatedOn = getDateValue(map, "updated_on")
    val customFields = createModels(map, "custom_fields", classOf[CustomFieldValue])
    new Issue(
      id.get,
      key.get,
      summary.get,
      description.get,
      url,
      dueDate,
      startDate,
      estimatedHours,
      actualHours,
      issueType,
      priority,
      resolution,
      status,
      components,
      versions,
      milestones,
      createdUser,
      assigner,
      createdOn,
      updatedOn,
      customFields)
  }
}