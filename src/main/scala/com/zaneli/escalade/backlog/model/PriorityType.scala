package com.zaneli.escalade.backlog.model

sealed abstract case class PriorityType protected (id: Int, displayName: String) {

}

object PriorityType {

  def apply(id: Int): PriorityType = id match {
    case High.id => High
    case Middle.id => Middle
    case Low.id => Low
  }

  object High extends PriorityType(2, "高")
  object Middle extends PriorityType(3, "中")
  object Low extends PriorityType(4, "低")
}