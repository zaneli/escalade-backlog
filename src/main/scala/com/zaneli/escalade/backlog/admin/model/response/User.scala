package com.zaneli.escalade.backlog.admin.model.response

import com.zaneli.escalade.backlog.model.response.ResponseModel
import com.zaneli.escalade.backlog.admin.model.RoleType
import com.zaneli.escalade.backlog.admin.model.response.User.MailSetting

case class User private (
  id: Int,
  userId: String,
  name: String,
  mailAddress: Option[String],
  role: Option[RoleType],
  mailSetting: Option[MailSetting],
  createdOn: Option[java.util.Date],
  updatedOn: Option[java.util.Date]) extends ResponseModel {

}

object User {
  def apply(map: Map[String, Any]): User = {
    import collection.JavaConversions._
    import com.zaneli.escalade.backlog.DataRetriever.{ getBooleanValue, getDateValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val userId = getStringValue(map, "user_id")
    val name = getStringValue(map, "name")
    val mailAddress = getStringValue(map, "mail_address")
    val role = getStringValue(map, "role") map { RoleType(_) }
    val mailSetting = map.get("mail_setting") map {
      _ match { case x: java.util.Map[String, Boolean] => MailSetting(x.getOrElse("mail", false), x.getOrElse("comment", false)) }
    }
    val createdOn = getDateValue(map, "created_on")
    val updatedOn = getDateValue(map, "updated_on")
    new User(id.get, userId.get, name.get, mailAddress, role, mailSetting, createdOn, updatedOn)
  }

  case class MailSetting private[User] (mail: Boolean, comment: Boolean) {
  }
}

