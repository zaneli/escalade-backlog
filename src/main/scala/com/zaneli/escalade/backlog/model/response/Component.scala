package com.zaneli.escalade.backlog.model.response

case class Component private (id: Int, name: String) extends ResponseModel {

}

object Component {
  private def apply(map: Map[String, Any]): Component = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    new Component(id.get, name.get)
  }
}