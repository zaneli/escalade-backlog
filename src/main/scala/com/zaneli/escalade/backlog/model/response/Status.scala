package com.zaneli.escalade.backlog.model.response

case class Status private (id: Int, name: String, count: Option[Int]) extends ResponseModel {

}

object Status {
  private def apply(map: Map[String, Any]): Status = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val count = getIntValue(map, "count")
    new Status(id.get, name.get, count)
  }
}
