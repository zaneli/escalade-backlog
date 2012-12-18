package com.zaneli.escalade.backlog.model.response

import com.zaneli.escalade.backlog.model.CustomFieldType

sealed abstract class CustomFieldValue protected (
  val id: Int,
  val name: String,
  val customType: CustomFieldType,
  val description: Option[String],
  val required: Boolean) extends ResponseModel {

}

object CustomFieldValue {
  private def apply(map: Map[String, Any]): CustomFieldValue = {
    import com.zaneli.escalade.backlog.DataRetriever.{ getBooleanValue, getDateValue, getDoubleValue, getIntValue, getIntArrayValue, getStringValue }
    val id = getIntValue(map, "id")
    val name = getStringValue(map, "name")
    val customType = CustomFieldType(getIntValue(map, "type_id").get)
    val description = getStringValue(map, "description")
    val required = getBooleanValue(map, "required")
    customType match {
      case CustomFieldType.String | CustomFieldType.Text => {
        val value = getStringValue(map, "value")
        new StringValue(id.get, name.get, customType, description, required.getOrElse(false), value)
      }
      case CustomFieldType.Number => {
        val value = getDoubleValue(map, "value")
        new NumberValue(id.get, name.get, customType, description, required.getOrElse(false), value)
      }
      case CustomFieldType.Date => {
        val value = getDateValue(map, "value")
        new DateValue(id.get, name.get, customType, description, required.getOrElse(false), value)
      }
      case CustomFieldType.List | CustomFieldType.Lists => {
        val values = getIntArrayValue(map, "values")
        new ListValue(id.get, name.get, customType, description, required.getOrElse(false), values)
      }
      case CustomFieldType.CheckBox | CustomFieldType.RadioButton => {
        val allowInput = getBooleanValue(map, "allow_input")
        val otherText = getStringValue(map, "other_text")
        val values = getIntArrayValue(map, "values")
        new SelectionValue(id.get, name.get, customType, description, required.getOrElse(false), allowInput.getOrElse(false), otherText, values)
      }
      case _ => {
        throw new Exception
      }
    }
  }

  case class StringValue private[CustomFieldValue] (
    override val id: Int,
    override val name: String,
    override val customType: CustomFieldType,
    override val description: Option[String],
    override val required: Boolean,
    value: Option[String]) extends CustomFieldValue(id, name, customType, description, required) {

  }

  case class NumberValue private[CustomFieldValue] (
    override val id: Int,
    override val name: String,
    override val customType: CustomFieldType,
    override val description: Option[String],
    override val required: Boolean,
    value: Option[Double]) extends CustomFieldValue(id, name, customType, description, required) {

  }

  case class DateValue private[CustomFieldValue] (
    override val id: Int,
    override val name: String,
    override val customType: CustomFieldType,
    override val description: Option[String],
    override val required: Boolean,
    value: Option[java.util.Date]) extends CustomFieldValue(id, name, customType, description, required) {

  }

  case class ListValue private[CustomFieldValue] (
    override val id: Int,
    override val name: String,
    override val customType: CustomFieldType,
    override val description: Option[String],
    override val required: Boolean,
    values: Array[Int]) extends CustomFieldValue(id, name, customType, description, required) {

  }

  case class SelectionValue private[CustomFieldValue] (
    override val id: Int,
    override val name: String,
    override val customType: CustomFieldType,
    override val description: Option[String],
    override val required: Boolean,
    allowInput: Boolean,
    otherText: Option[String],
    values: Array[Int]) extends CustomFieldValue(id, name, customType, description, required) {

  }
}