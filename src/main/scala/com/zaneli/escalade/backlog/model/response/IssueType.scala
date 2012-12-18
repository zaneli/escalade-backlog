package com.zaneli.escalade.backlog.model.response

import com.zaneli.escalade.backlog.model.ColorType

case class IssueType private (
  id: Int, name: String, color: ColorType) extends ResponseModel {

}

object IssueType {
  private def apply(map: Map[String, Any]): IssueType = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val color = ColorType(getStringValue(map, "color").get)
    new IssueType(id.get, name.get, color)
  }
}