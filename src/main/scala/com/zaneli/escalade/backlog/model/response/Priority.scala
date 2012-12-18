package com.zaneli.escalade.backlog.model.response

case class Priority private (id: Int, name: String) extends ResponseModel {

}

object Priority {
  private def apply(map: Map[String, Any]): Priority = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    new Priority(id.get, name.get)
  }
}
