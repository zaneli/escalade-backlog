package com.zaneli.escalade.backlog.model

sealed abstract case class StatusType protected (id: Int, displayName: String) {

}

object StatusType {

  def apply(id: Int): StatusType = id match {
    case NotSupported.id => NotSupported
    case Processing.id => Processing
    case Processed.id => Processed
    case Completed.id => Completed
  }

  object NotSupported extends StatusType(1, "未対応")
  object Processing extends StatusType(2, "処理中")
  object Processed extends StatusType(3, "処理済み")
  object Completed extends StatusType(4, "完了")
}