package com.zaneli.escalade.backlog.model.response

case class Comment private (
  id: Int,
  content: String,
  createdUser: User,
  createdOn: java.util.Date,
  updatedOn: java.util.Date) extends ResponseModel {

}

object Comment {
  private def apply(map: Map[String, Any]): Comment = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModel, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val content = getStringValue(map, "content")
    val createdUser = createModel(map, "created_user", classOf[User])
    val createdOn = getDateValue(map, "created_on")
    val updatedOn = getDateValue(map, "updated_on")
    new Comment(id.get, content.get, createdUser.get, createdOn.get, updatedOn.get)
  }
}