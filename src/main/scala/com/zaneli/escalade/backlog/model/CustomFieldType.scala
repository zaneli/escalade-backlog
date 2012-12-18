package com.zaneli.escalade.backlog.model

sealed abstract case class CustomFieldType protected (id: Int, displayName: String) {

}

object CustomFieldType {

  def apply(id: Int): CustomFieldType = id match {
    case String.id => String
    case Text.id => Text
    case Number.id => Number
    case Date.id => Date
    case List.id => List
    case Lists.id => Lists
    case CheckBox.id => CheckBox
    case RadioButton.id => RadioButton
  }

  object String extends CustomFieldType(1, "文字列")
  object Text extends CustomFieldType(2, "文章")
  object Number extends CustomFieldType(3, "数値")
  object Date extends CustomFieldType(4, "日付")
  object List extends CustomFieldType(5, "単一リスト")
  object Lists extends CustomFieldType(6, "複数リスト")
  object CheckBox extends CustomFieldType(7, "チェックボックス")
  object RadioButton extends CustomFieldType(8, "ラジオ")
}
