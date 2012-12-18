package com.zaneli.escalade.backlog.admin.model

sealed abstract case class RoleType protected (value: String, displayName: String) {

}

object RoleType {

  def apply(value: String): RoleType = value.toLowerCase match {
    case Admin.value => Admin
    case NormalUser.value => NormalUser
    case Reporter.value => Reporter
    case Viewer.value => Viewer
    case GuestReporter.value => GuestReporter
    case GuestViewer.value => GuestViewer
  }

  object Admin extends RoleType("admin", "管理者")
  object NormalUser extends RoleType("normal-user", "一般ユーザ")
  object Reporter extends RoleType("reporter", "レポータ")
  object Viewer extends RoleType("viewer", "ビューア")
  object GuestReporter extends RoleType("guest-reporter", "ゲストレポータ")
  object GuestViewer extends RoleType("guest-viewer", "ゲストビューア")
}
