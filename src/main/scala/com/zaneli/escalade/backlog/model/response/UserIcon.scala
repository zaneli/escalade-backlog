package com.zaneli.escalade.backlog.model.response

case class UserIcon private (
  id: Int,
  contentType: String,
  data: Array[Byte],
  updatedOn: Option[java.util.Date]) extends ResponseModel {

}

object UserIcon {
  def apply(map: Map[String, Any]): UserIcon = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getByteArrayValue, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val contentType = getStringValue(map, "content_type")
    val data = getByteArrayValue(map, "data")
    val updatedOn = getDateValue(map, "updated_on")
    new UserIcon(id.get, contentType.get, data, updatedOn)
  }
}