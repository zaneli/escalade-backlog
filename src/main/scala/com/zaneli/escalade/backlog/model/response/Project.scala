package com.zaneli.escalade.backlog.model.response

case class Project private (
  id: Int, name: String, key: String, url: String, archived: Boolean) extends ResponseModel {

}

object Project {
  private def apply(map: Map[String, Any]): Project = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getBooleanValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val key = getStringValue(map, "key")
    val url = getStringValue(map, "url")
    val archived = getBooleanValue(map, "archived")
    new Project(id.get, name.get, key.get, url.get, archived.get)
  }
}