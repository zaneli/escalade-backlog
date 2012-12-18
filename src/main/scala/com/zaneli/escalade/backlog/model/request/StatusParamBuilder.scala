package com.zaneli.escalade.backlog.model.request

import com.zaneli.escalade.backlog.model.{ ResolutionType, StatusType }

sealed abstract class StatusParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params) {

}

class SwitchStatusParamBuilder private (params: Map[String, Any])
  extends StatusParamBuilder[SwitchStatusParamBuilder.SwitchStatusParam](params) {

  type This = SwitchStatusParamBuilder

  def assignerId(assignerId: Int): This = {
    newBuilder("assignerId", assignerId)
  }

  def resolution(resolution: ResolutionType): This = {
    newBuilder("resolutionId", resolution.id)
  }

  def comment(comment: String): This = {
    newBuilder("comment", comment)
  }

  override def build(): SwitchStatusParamBuilder.SwitchStatusParam = {
    new SwitchStatusParamBuilder.SwitchStatusParam(params)
  }
}

object SwitchStatusParamBuilder {
  def apply(key: String, status: StatusType): SwitchStatusParamBuilder = {
    new SwitchStatusParamBuilder(Map("key" -> key, "statusId" -> status.id))
  }
  class SwitchStatusParam private[SwitchStatusParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}
