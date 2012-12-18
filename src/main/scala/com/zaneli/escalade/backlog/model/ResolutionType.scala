package com.zaneli.escalade.backlog.model

sealed abstract case class ResolutionType protected (id: Int, displayName: String) {

}

object ResolutionType {

  def apply(id: Int): ResolutionType = id match {
    case NotSetting.id => NotSetting
    case Corresponded.id => Corresponded
    case NotCorrespond.id => NotCorrespond
    case Invalid.id => Invalid
    case Duplicated.id => Duplicated
    case NotReproduced.id => NotReproduced
  }

  object NotSetting extends ResolutionType(-1, "未設定")
  object Corresponded extends ResolutionType(0, "対応済み")
  object NotCorrespond extends ResolutionType(1, "対応しない")
  object Invalid extends ResolutionType(2, "無効")
  object Duplicated extends ResolutionType(3, "重複")
  object NotReproduced extends ResolutionType(4, "再現しない")
}