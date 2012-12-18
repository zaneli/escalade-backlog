package com.zaneli.escalade.backlog.model.request

import com.zaneli.escalade.backlog.model.FormatedDate

sealed abstract class IssueConditionParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params) {

  def issueTypeId(issueTypeId: Int*): This = {
    newBuilder("issueTypeId", issueTypeId)
  }

  def issueType(issueType: String*): This = {
    newBuilder("issueType", issueType)
  }

  def componentId(componentId: Int*): This = {
    newBuilder("componentId", componentId)
  }

  def versionId(versionId: Int*): This = {
    newBuilder("versionId", versionId)
  }

  def milestoneId(milestoneId: Int*): This = {
    newBuilder("milestoneId", milestoneId)
  }

  import com.zaneli.escalade.backlog.model.StatusType
  def status(status: StatusType*): This = {
    newBuilder("statusId", status)
  }

  import com.zaneli.escalade.backlog.model.PriorityType
  def priority(priority: PriorityType*): This = {
    newBuilder("priorityId", priority)
  }

  def assignerId(assignerId: Int*): This = {
    newBuilder("assignerId", assignerId)
  }

  def createdUserId(createdUserId: Int*): This = {
    newBuilder("createdUserId", createdUserId)
  }

  import com.zaneli.escalade.backlog.model.ResolutionType
  def resolution(resolution: ResolutionType*): This = {
    newBuilder("resolutionId", resolution)
  }

  def createdOnMin(createdOnMin: java.util.Date): This = {
    newBuilder("created_on_min", new FormatedDate(createdOnMin, "yyyyMMdd"))
  }

  def createdOnMax(createdOnMax: java.util.Date): This = {
    newBuilder("created_on_max", new FormatedDate(createdOnMax, "yyyyMMdd"))
  }

  def updatedOnMin(updatedOnMin: java.util.Date): This = {
    newBuilder("updated_on_min", new FormatedDate(updatedOnMin, "yyyyMMdd"))
  }

  def updatedOnMax(updatedOnMax: java.util.Date): This = {
    newBuilder("updated_on_max", new FormatedDate(updatedOnMax, "yyyyMMdd"))
  }

  def startDateMin(startDateMin: java.util.Date): This = {
    newBuilder("start_date_min", new FormatedDate(startDateMin, "yyyyMMdd"))
  }

  def startDateMax(startDateMax: java.util.Date): This = {
    newBuilder("start_date_max", new FormatedDate(startDateMax, "yyyyMMdd"))
  }

  def dueDateMin(dueDateMin: java.util.Date): This = {
    newBuilder("due_date_min", new FormatedDate(dueDateMin, "yyyyMMdd"))
  }

  def dueDateMax(dueDateMax: java.util.Date): This = {
    newBuilder("due_date_max", new FormatedDate(dueDateMax, "yyyyMMdd"))
  }

  def query(query: String): This = {
    newBuilder("query", query)
  }

  import com.zaneli.escalade.backlog.model.FileType
  def file(file: FileType*): This = {
    newBuilder("file", file)
  }

  def customFields(customFields: IssueConditionParamBuilder.CustomFieldParam*): This = {
    if (customFields.isEmpty) {
      return this.asInstanceOf
    }
    newBuilder("custom_fields", customFields.toArray)
  }
}

object IssueConditionParamBuilder {
  class CustomFieldParam private (params: Map[String, Any]) extends RequestModel(params) {
  }

  object CustomFieldParam {
    def apply(id: Int, values: String): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "values" -> values));
    }
    def apply(id: Int, min: Double, max: Double): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "min" -> min, "max" -> max));
    }
    def apply(id: Int, min: java.util.Date, max: java.util.Date): CustomFieldParam = {
      new CustomFieldParam(
        Map("id" -> id, "min" -> new FormatedDate(min, "yyyyMMdd"), "max" -> new FormatedDate(max, "yyyyMMdd")));
    }
    def apply(id: Int, values: Int*): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "values" -> values));
    }
    def apply(id: Int, otherText: Boolean, values: Int*): CustomFieldParam = {
      new CustomFieldParam(Map("id" -> id, "other_text" -> otherText, "values" -> values));
    }
  }
}

class CountIssueParamBuilder private (params: Map[String, Any])
  extends IssueConditionParamBuilder[CountIssueParamBuilder.CountIssueParam](params) {

  type This = CountIssueParamBuilder

  override def build(): CountIssueParamBuilder.CountIssueParam = {
    new CountIssueParamBuilder.CountIssueParam(params)
  }
}

object CountIssueParamBuilder {
  def apply(projectId: Int): CountIssueParamBuilder = {
    new CountIssueParamBuilder(Map("projectId" -> projectId))
  }
  class CountIssueParam private[CountIssueParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}

class FindIssueParamBuilder private (params: Map[String, Any])
  extends IssueConditionParamBuilder[FindIssueParamBuilder.FindIssueParam](params) {

  type This = FindIssueParamBuilder

  def sort(sort: String): This = {
    newBuilder("sort", sort)
  }

  def order(order: Boolean): This = {
    newBuilder("order", order)
  }

  def offset(offset: Int): This = {
    newBuilder("offset", offset)
  }

  def limit(limit: Int): This = {
    newBuilder("limit", limit)
  }

  override def build(): FindIssueParamBuilder.FindIssueParam = {
    new FindIssueParamBuilder.FindIssueParam(params)
  }
}

object FindIssueParamBuilder {
  def apply(projectId: Int): FindIssueParamBuilder = {
    new FindIssueParamBuilder(Map("projectId" -> projectId))
  }
  class FindIssueParam private[FindIssueParamBuilder] (params: Map[String, Any])
    extends RequestModel(params) {
  }
}