package com.zaneli.escalade.backlog.admin.model.response

import com.zaneli.escalade.backlog.model.response.ResponseModel

case class Project private (
  id: Int,
  name: String,
  key: String,
  url: String,
  useChart: Option[Boolean],
  archived: Boolean,
  createdOn: Option[java.util.Date],
  updatedOn: Option[java.util.Date]) extends ResponseModel {

}

object Project {
  private def apply(map: Map[String, Any]): Project = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getBooleanValue, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val key = getStringValue(map, "key")
    val url = getStringValue(map, "url")
    val useChart = getBooleanValue(map, "use_chart")
    val archived = getBooleanValue(map, "archived")
    val createdOn = getDateValue(map, "created_on")
    val updatedOn = getDateValue(map, "updated_on")
    new Project(id.get, name.get, key.get, url.get, useChart, archived.get, createdOn, updatedOn)
  }
}