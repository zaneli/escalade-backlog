package com.zaneli.escalade.backlog.admin.model.request

import com.zaneli.escalade.backlog.model.request.{ RequestModel, RequestParamBuilder }

sealed abstract class CustomFieldParamBuilder[A <: RequestModel] private[request] (params: Map[String, Any])
  extends RequestParamBuilder[A](params: Map[String, Any]) {

  import com.zaneli.escalade.backlog.model.FormatedDate

  def description(description: String): This = {
    newBuilder("description", description)
  }

  def required(required: Boolean): This = {
    newBuilder("required", required)
  }

  def numField(min: Double, max: Double, initialValue: Double, unit: String): This = {
    newBuilder(Map("min" -> min, "max" -> max, "initial_value" -> initialValue, "unit" -> unit))
  }

  def dateField(min: java.util.Date, max: java.util.Date): This = {
    newBuilder(Map(
      "initial_value_type" -> 1,
      "min" -> new FormatedDate(min, "yyyyMMdd"),
      "max" -> new FormatedDate(max, "yyyyMMdd")))
  }

  def dateField(initialShift: Int, min: java.util.Date, max: java.util.Date): This = {
    newBuilder(Map(
      "initial_value_type" -> 2,
      "initial_shift" -> initialShift,
      "min" -> new FormatedDate(min, "yyyyMMdd"),
      "max" -> new FormatedDate(max, "yyyyMMdd")))
  }

  def dateField(initialDate: java.util.Date, min: java.util.Date, max: java.util.Date): This = {
    newBuilder(Map(
      "initial_value_type" -> 3,
      "initial_date" -> new FormatedDate(initialDate, "yyyyMMdd"),
      "min" -> new FormatedDate(min, "yyyyMMdd"),
      "max" -> new FormatedDate(max, "yyyyMMdd")))
  }

  def listField(items: String*): This = {
    newBuilder(Map("items" -> items.map(item => Map("name" -> item)).toArray))
  }

  def selectionField(allowInput: Boolean, items: String*): This = {
    newBuilder(Map("allow_input" -> allowInput, "items" -> items.map(item => Map("name" -> item)).toArray))
  }
}

class AddCustomFieldParamBuilder private (params: Map[String, Any])
  extends CustomFieldParamBuilder[AddCustomFieldParamBuilder.AddCustomFieldParam](params: Map[String, Any]) {

  type This = AddCustomFieldParamBuilder

  override def build(): AddCustomFieldParamBuilder.AddCustomFieldParam = {
    new AddCustomFieldParamBuilder.AddCustomFieldParam(params)
  }
}

object AddCustomFieldParamBuilder {
  import com.zaneli.escalade.backlog.model.CustomFieldType

  def apply(projectId: Int, customType: CustomFieldType, name: String, issueTypes: Int*): AddCustomFieldParamBuilder = {
    new AddCustomFieldParamBuilder(
      Map("projectId" -> projectId, "typeId" -> customType.id, "name" -> name, "issueTypes" -> issueTypes))
  }
  class AddCustomFieldParam private[AddCustomFieldParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}

class UpdateCustomFieldParamBuilder private (params: Map[String, Any])
  extends CustomFieldParamBuilder[UpdateCustomFieldParamBuilder.UpdateCustomFieldParam](params: Map[String, Any]) {

  type This = UpdateCustomFieldParamBuilder

  override def build(): UpdateCustomFieldParamBuilder.UpdateCustomFieldParam = {
    new UpdateCustomFieldParamBuilder.UpdateCustomFieldParam(params)
  }
}

object UpdateCustomFieldParamBuilder {
  def apply(id: Int, name: String, issueTypes: Int*): UpdateCustomFieldParamBuilder = {
    new UpdateCustomFieldParamBuilder(Map("id" -> id, "name" -> name, "issueTypes" -> issueTypes))
  }
  class UpdateCustomFieldParam private[UpdateCustomFieldParamBuilder] (params: Map[String, Any])
    extends RequestModel(params: Map[String, Any]) {
  }
}
