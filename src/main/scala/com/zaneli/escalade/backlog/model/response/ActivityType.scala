package com.zaneli.escalade.backlog.model.response

case class ActivityType private (id: Int, name: String) extends ResponseModel {

}

object ActivityType {
  private def apply(map: Map[String, Any]): ActivityType = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    new ActivityType(id.get, name.get)
  }
}