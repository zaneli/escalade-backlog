package com.zaneli.escalade.backlog.model.response

case class Resolution private (id: Int, name: String) extends ResponseModel {

}

object Resolution {
  private def apply(map: Map[String, Any]): Resolution = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    new Resolution(id.get, name.get)
  }
}