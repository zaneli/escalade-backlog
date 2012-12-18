package com.zaneli.escalade.backlog.model.response

case class User private (
  id: Int,
  name: String,
  lang: Option[String],
  updatedOn: Option[java.util.Date]) extends ResponseModel {

}

object User {
  def apply(map: Map[String, Any]): User = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val lang = getStringValue(map, "lang")
    val updatedOn = getDateValue(map, "updated_on")
    new User(id.get, name.get, lang, updatedOn)
  }
}