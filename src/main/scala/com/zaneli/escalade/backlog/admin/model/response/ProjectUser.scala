package com.zaneli.escalade.backlog.admin.model.response

import com.zaneli.escalade.backlog.model.response.ResponseModel

case class ProjectUser private (id: Int, userId: String, name: String) extends ResponseModel {

}

object ProjectUser {
  def apply(map: Map[String, Any]): ProjectUser = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val userId = getStringValue(map, "user_id")
    val name = getStringValue(map, "name")
    new ProjectUser(id.get, userId.get, name.get)
  }
}