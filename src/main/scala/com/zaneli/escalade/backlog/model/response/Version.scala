package com.zaneli.escalade.backlog.model.response

case class Version private (
  id: Int,
  name: String,
  date: Option[java.util.Date],
  startDate: Option[java.util.Date],
  dueDate: Option[java.util.Date],
  archived: Option[Boolean]) extends ResponseModel {

}

object Version {
  private def apply(map: Map[String, Any]): Version = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getBooleanValue, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val date = getDateValue(map, "date")
    val startDate = getDateValue(map, "start_date")
    val dueDate = getDateValue(map, "due_date")
    val archived = getBooleanValue(map, "archived")
    new Version(id.get, name.get, date, startDate, dueDate, archived)
  }
}