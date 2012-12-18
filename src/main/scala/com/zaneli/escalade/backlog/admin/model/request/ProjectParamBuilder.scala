package com.zaneli.escalade.backlog.admin.model.request

import com.zaneli.escalade.backlog.model.request.{ RequestModel, RequestParamBuilder }

sealed abstract class ProjectParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params: Map[String, Any]) {

  def useChart(useChart: Boolean): This = {
    newBuilder("use_chart", useChart)
  }
}

class AddProjectParamBuilder private (params: Map[String, Any])
  extends ProjectParamBuilder[AddProjectParamBuilder.AddProjectParam](params: Map[String, Any]) {

  type This = AddProjectParamBuilder

  override def build(): AddProjectParamBuilder.AddProjectParam = {
    new AddProjectParamBuilder.AddProjectParam(params)
  }
}

object AddProjectParamBuilder {
  def apply(name: String, key: String): AddProjectParamBuilder = {
    import java.security.MessageDigest
    new AddProjectParamBuilder(Map("name" -> name, "key" -> key))
  }
  class AddProjectParam private[AddProjectParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}

class UpdateProjectParamBuilder private (params: Map[String, Any])
  extends ProjectParamBuilder[UpdateProjectParamBuilder.UpdateProjectParam](params: Map[String, Any]) {

  type This = UpdateProjectParamBuilder

  def name(name: String): This = {
    newBuilder("name", name)
  }

  def key(key: String): This = {
    newBuilder("key", key)
  }

  def archived(archived: Boolean): This = {
    newBuilder("archived", archived)
  }

  override def build(): UpdateProjectParamBuilder.UpdateProjectParam = {
    new UpdateProjectParamBuilder.UpdateProjectParam(params)
  }
}

object UpdateProjectParamBuilder {
  def apply(id: Int): UpdateProjectParamBuilder = {
    new UpdateProjectParamBuilder(Map("id" -> id))
  }
  class UpdateProjectParam private[UpdateProjectParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}
