package com.zaneli.escalade.backlog.admin.model.request

import com.zaneli.escalade.backlog.admin.model.RoleType
import com.zaneli.escalade.backlog.model.request.{ RequestModel, RequestParamBuilder }

sealed abstract class UserParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params: Map[String, Any]) {

  def mailSetting(mail: Boolean, comment: Boolean): This = {
    newBuilder("mail_setting", Map("mail" -> mail, "comment" -> comment))
  }

  def icon(iconName: String): This = {
    newBuilder("icon", Map("type" -> "backlog", "data" -> iconName))
  }

  def icon(iconData: Array[Byte]): This = {
    newBuilder("icon", Map("type" -> "original", "data" -> iconData))
  }
}

class AddUserParamBuilder private (params: Map[String, Any])
  extends UserParamBuilder[AddUserParamBuilder.AddUserParam](params: Map[String, Any]) {

  type This = AddUserParamBuilder

  override def build(): AddUserParamBuilder.AddUserParam = {
    new AddUserParamBuilder.AddUserParam(params)
  }
}

object AddUserParamBuilder {
  def apply(userId: String, password: String, name: String, mailAddress: String, role: RoleType): AddUserParamBuilder = {
    import java.security.MessageDigest
    new AddUserParamBuilder(Map(
      "user_id" -> userId,
      "password_md5" -> PasswordUtil.md5Hex(password),
      "name" -> name,
      "mail_address" -> mailAddress,
      "role" -> role.value))
  }
  class AddUserParam private[AddUserParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}

class UpdateUserParamBuilder private (params: Map[String, Any])
  extends UserParamBuilder[UpdateUserParamBuilder.UpdateUserParam](params: Map[String, Any]) {

  type This = UpdateUserParamBuilder

  def password(password: String): This = {
    newBuilder("password_md5", PasswordUtil.md5Hex(password))
  }

  def name(name: String): This = {
    newBuilder("name", name)
  }

  def mailAddress(mailAddress: String): This = {
    newBuilder("mail_address", mailAddress)
  }

  def role(role: RoleType): This = {
    newBuilder("role", role.value)
  }

  override def build(): UpdateUserParamBuilder.UpdateUserParam = {
    new UpdateUserParamBuilder.UpdateUserParam(params)
  }
}

object UpdateUserParamBuilder {
  def apply(id: Int): UpdateUserParamBuilder = {
    new UpdateUserParamBuilder(Map("id" -> id))
  }
  class UpdateUserParam private[UpdateUserParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}

private object PasswordUtil {
  private[request] def md5Hex(org: String): String = {
    import java.security.MessageDigest
    MessageDigest.getInstance("MD5").digest(org.getBytes).map("%02x".format(_)).mkString
  }
}
