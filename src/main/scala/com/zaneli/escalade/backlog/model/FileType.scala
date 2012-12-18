package com.zaneli.escalade.backlog.model

sealed abstract case class FileType protected (id: Int, displayName: String) {

}

object FileType {

  def apply(id: Int): FileType = id match {
    case AttachedFile.id => AttachedFile
    case SharedFileLink.id => SharedFileLink
  }

  object AttachedFile extends FileType(1, "添付ファイル有り")
  object SharedFileLink extends FileType(2, "共有ファイルリンク有り")
}
