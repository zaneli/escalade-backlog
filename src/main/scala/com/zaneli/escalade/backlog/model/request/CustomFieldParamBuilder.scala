package com.zaneli.escalade.backlog.model.request

sealed abstract class CustomFieldParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params) {

}

class GetCustomFieldParamBuilder private (params: Map[String, Any])
  extends CustomFieldParamBuilder[GetCustomFieldParamBuilder.GetCustomFieldParam](params) {

  type This = GetCustomFieldParamBuilder

  def issueTypeId(issueTypeId: Int*): This = {
    newBuilder("issueTypeId", issueTypeId)
  }

  def issueType(issueType: String*): This = {
    newBuilder("issueType", issueType)
  }

  override def build(): GetCustomFieldParamBuilder.GetCustomFieldParam = {
    new GetCustomFieldParamBuilder.GetCustomFieldParam(params)
  }
}

object GetCustomFieldParamBuilder {
  def apply(projectId: Int): GetCustomFieldParamBuilder = {
    new GetCustomFieldParamBuilder(Map("projectId" -> projectId))
  }
  class GetCustomFieldParam private[GetCustomFieldParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}
