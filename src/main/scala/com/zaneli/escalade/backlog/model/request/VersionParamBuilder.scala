package com.zaneli.escalade.backlog.model.request

sealed abstract class VersionParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params) {

  import com.zaneli.escalade.backlog.model.FormatedDate

  def startDate(startDate: java.util.Date): This = {
    newBuilder("start_date", new FormatedDate(startDate, "yyyyMMdd"))
  }

  def dueDate(dueDate: java.util.Date): This = {
    newBuilder("due_date", new FormatedDate(dueDate, "yyyyMMdd"))
  }
}

class AddVersionParamBuilder private (params: Map[String, Any])
  extends VersionParamBuilder[AddVersionParamBuilder.AddVersionParam](params) {

  type This = AddVersionParamBuilder

  override def build(): AddVersionParamBuilder.AddVersionParam = {
    new AddVersionParamBuilder.AddVersionParam(params)
  }
}

object AddVersionParamBuilder {
  def apply(projectId: Int, name: String): AddVersionParamBuilder = {
    new AddVersionParamBuilder(Map("project_id" -> projectId, "name" -> name))
  }
  class AddVersionParam private[AddVersionParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}

class UpdateVersionParamBuilder private (params: Map[String, Any])
  extends VersionParamBuilder[UpdateVersionParamBuilder.UpdateVersionParam](params) {

  type This = UpdateVersionParamBuilder

  def archived(archived: Boolean): This = {
    newBuilder("archived", archived)
  }

  override def build(): UpdateVersionParamBuilder.UpdateVersionParam = {
    new UpdateVersionParamBuilder.UpdateVersionParam(params)
  }
}

object UpdateVersionParamBuilder {
  def apply(id: Int, name: String): UpdateVersionParamBuilder = {
    new UpdateVersionParamBuilder(Map("id" -> id, "name" -> name))
  }
  class UpdateVersionParam private[UpdateVersionParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}