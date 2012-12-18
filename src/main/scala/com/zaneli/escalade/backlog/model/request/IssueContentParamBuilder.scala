package com.zaneli.escalade.backlog.model.request

import com.zaneli.escalade.backlog.model.{ FormatedDate, PriorityType, ResolutionType }

sealed abstract class IssueContentParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params) {

  def description(description: String): This = {
    newBuilder("description", description)
  }

  def startDate(startDate: java.util.Date): This = {
    newBuilder("start_date", new FormatedDate(startDate, "yyyyMMdd"))
  }

  def dueDate(dueDate: java.util.Date): This = {
    newBuilder("due_date", new FormatedDate(dueDate, "yyyyMMdd"))
  }

  def estimatedHours(estimatedHours: Double): This = {
    newBuilder("estimated_hours", estimatedHours)
  }

  def actualHours(actualHours: Double): This = {
    newBuilder("actual_hours", actualHours)
  }

  def issueTypeId(issueTypeId: Int): This = {
    newBuilder("issueTypeId", issueTypeId)
  }

  def issueType(issueType: String): This = {
    newBuilder("issueType", issueType)
  }

  def componentId(componentId: Int*): This = {
    newBuilder("componentId", componentId)
  }

  def component(component: String*): This = {
    newBuilder("component", component)
  }

  def versionId(versionId: Int*): This = {
    newBuilder("versionId", versionId)
  }

  def version(version: String*): This = {
    newBuilder("version", version)
  }

  def milestoneId(milestoneId: Int*): This = {
    newBuilder("milestoneId", milestoneId)
  }

  def milestone(milestone: String*): This = {
    newBuilder("milestone", milestone)
  }

  def priority(priority: PriorityType): This = {
    newBuilder("priorityId", priority.id)
  }

  def assignerId(assignerId: Int): This = {
    newBuilder("assignerId", assignerId)
  }

  def customFields(customFields: IssueContentParamBuilder.CustomFieldParam*): This = {
    import collection.JavaConversions._
    if (customFields.isEmpty) {
      return this.asInstanceOf
    }
    newBuilder("custom_fields", customFields.toArray)
  }
}

object IssueContentParamBuilder {
  class CustomFieldParam private (params: Map[String, Any]) extends RequestModel(params) {

  }

  object CustomFieldParam {
    def apply(id: Int, values: String): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "values" -> values));
    }
    def apply(id: Int, otherText: String, values: Int*): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "other_text" -> otherText, "values" -> values));
    }
  }
}

class CreateIssueParamBuilder private (params: Map[String, Any])
  extends IssueContentParamBuilder[CreateIssueParamBuilder.CreateIssueParam](params) {

  type This = CreateIssueParamBuilder

  override def build(): CreateIssueParamBuilder.CreateIssueParam = {
    new CreateIssueParamBuilder.CreateIssueParam(params)
  }
}

object CreateIssueParamBuilder {
  def apply(projectId: Int, summary: String): CreateIssueParamBuilder = {
    new CreateIssueParamBuilder(Map("projectId" -> projectId, "summary" -> summary))
  }
  class CreateIssueParam private[CreateIssueParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}

class UpdateIssueParamBuilder private (params: Map[String, Any])
  extends IssueContentParamBuilder[UpdateIssueParamBuilder.UpdateIssueParam](params) {

  type This = UpdateIssueParamBuilder

  def summary(summary: String): This = {
    newBuilder("summary", summary)
  }

  def resolution(resolution: ResolutionType): This = {
    newBuilder("resolutionId", resolution.id)
  }

  def comment(comment: String): This = {
    newBuilder("comment", comment)
  }

  override def build(): UpdateIssueParamBuilder.UpdateIssueParam = {
    new UpdateIssueParamBuilder.UpdateIssueParam(params)
  }
}

object UpdateIssueParamBuilder {
  def apply(key: String): UpdateIssueParamBuilder = {
    new UpdateIssueParamBuilder(Map("key" -> key))
  }
  class UpdateIssueParam private[UpdateIssueParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}