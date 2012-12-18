package com.zaneli.escalade.backlog.model.response

case class Milestone private (
  id: Int,
  name: String,
  date: Option[java.util.Date],
  dueDate: Option[java.util.Date],
  statuses: Array[Status],
  burndownChart: Array[Byte]) extends ResponseModel {

}

object Milestone {
  private def apply(map: Map[String, Any]): Milestone = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModels, getByteArrayValue, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val date = getDateValue(map, "date")
    val dueDate = getDateValue(map, "due_date")
    val statuses = createModels(map, "statuses", classOf[Status])
    val burndownChart = getByteArrayValue(map, "burndown_chart")
    new Milestone(id.get, name.get, date, dueDate, statuses, burndownChart)
  }
}
