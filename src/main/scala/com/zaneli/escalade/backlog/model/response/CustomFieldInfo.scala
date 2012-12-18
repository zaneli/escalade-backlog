package com.zaneli.escalade.backlog.model.response

import com.zaneli.escalade.backlog.model.CustomFieldType

sealed abstract class CustomFieldInfo protected (
  val id: Int,
  val customType: CustomFieldType,
  val name: String,
  val description: Option[String],
  val required: Boolean,
  val issueTypes: Array[IssueType]) extends ResponseModel {

}

object CustomFieldInfo {
  private def apply(map: Map[String, Any]): CustomFieldInfo = {
    import com.zaneli.escalade.backlog.DataRetriever.{ createModels, getBooleanValue, getDateValue, getDoubleValue, getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val customType = CustomFieldType(getIntValue(map, "type_id").get)
    val name = getStringValue(map, "name")
    val description = getStringValue(map, "description")
    val required = getBooleanValue(map, "required")
    val issueTypes = createModels(map, "issueTypes", classOf[IssueType])
    customType match {
      case CustomFieldType.String | CustomFieldType.Text => {
        new StringInfo(id.get, customType, name.get, description, required.getOrElse(false), issueTypes)
      }
      case CustomFieldType.Number => {
        val min = getDoubleValue(map, "min")
        val max = getDoubleValue(map, "max")
        val initialValue = getDoubleValue(map, "initial_value")
        val unit = getStringValue(map, "unit")
        new NumberInfo(id.get, customType, name.get, description, required.getOrElse(false), issueTypes, min, max, initialValue, unit)
      }
      case CustomFieldType.Date => {
        val initialValueType = getIntValue(map, "initial_value_type")
        val initialShift = getIntValue(map, "initial_shift")
        val initialDate = getDateValue(map, "initial_date")
        val min = getDateValue(map, "min")
        val max = getDateValue(map, "max")
        new DateInfo(id.get, customType, name.get, description, required.getOrElse(false), issueTypes, initialValueType.get, initialShift, initialDate, min, max)
      }
      case CustomFieldType.List | CustomFieldType.Lists => {
        val items = createModels(map, "items", classOf[Item])
        new ListInfo(id.get, customType, name.get, description, required.getOrElse(false), issueTypes, items)
      }
      case CustomFieldType.CheckBox | CustomFieldType.RadioButton => {
        val allowInput = getBooleanValue(map, "allow_input")
        val items = createModels(map, "items", classOf[Item])
        new SelectionInfo(id.get, customType, name.get, description, required.getOrElse(false), issueTypes, allowInput.getOrElse(false), items)
      }
    }
  }

  case class StringInfo private[CustomFieldInfo] (
    override val id: Int,
    override val customType: CustomFieldType,
    override val name: String,
    override val description: Option[String],
    override val required: Boolean,
    override val issueTypes: Array[IssueType]) extends CustomFieldInfo(id, customType, name, description, required, issueTypes) {

  }

  case class NumberInfo private[CustomFieldInfo] (
    override val id: Int,
    override val customType: CustomFieldType,
    override val name: String,
    override val description: Option[String],
    override val required: Boolean,
    override val issueTypes: Array[IssueType],
    min: Option[Double],
    max: Option[Double],
    initialValue: Option[Double],
    unit: Option[String]) extends CustomFieldInfo(id, customType, name, description, required, issueTypes) {

  }

  case class DateInfo private[CustomFieldInfo] (
    override val id: Int,
    override val customType: CustomFieldType,
    override val name: String,
    override val description: Option[String],
    override val required: Boolean,
    override val issueTypes: Array[IssueType],
    initialValueType: Int,
    initialShift: Option[Int],
    initialDate: Option[java.util.Date],
    min: Option[java.util.Date],
    max: Option[java.util.Date]) extends CustomFieldInfo(id, customType, name, description, required, issueTypes) {

  }

  case class ListInfo private[CustomFieldInfo] (
    override val id: Int,
    override val customType: CustomFieldType,
    override val name: String,
    override val description: Option[String],
    override val required: Boolean,
    override val issueTypes: Array[IssueType],
    items: Array[Item]) extends CustomFieldInfo(id, customType, name, description, required, issueTypes) {

  }

  case class SelectionInfo private[CustomFieldInfo] (
    override val id: Int,
    override val customType: CustomFieldType,
    override val name: String,
    override val description: Option[String],
    override val required: Boolean,
    override val issueTypes: Array[IssueType],
    allowInput: Boolean,
    items: Array[Item]) extends CustomFieldInfo(id, customType, name, description, required, issueTypes) {

  }
}

case class Item private (id: Int, name: String) extends ResponseModel {

}

object Item {
  private def apply(map: Map[String, Any]): Item = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getIntValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    new Item(id.get, name.get)
  }
}
