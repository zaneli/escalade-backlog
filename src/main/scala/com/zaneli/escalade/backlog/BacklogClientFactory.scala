package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.admin.BacklogAdminClient

class BacklogClientFactory private (spaceId: String, username: String, password: String) {

  @throws(classOf[BacklogException])
  def createClient(): BacklogClient = {
    new BacklogClient(spaceId, username, password)
  }

  @throws(classOf[BacklogException])
  def createAdminClient(): BacklogAdminClient = {
    new BacklogAdminClient(spaceId, username, password)
  }
}

object BacklogClientFactory {
  def apply(spaceId: String, username: String, password: String): BacklogClientFactory = {
    new BacklogClientFactory(spaceId, username, password)
  }
}