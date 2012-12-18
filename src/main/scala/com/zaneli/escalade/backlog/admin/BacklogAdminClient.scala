package com.zaneli.escalade.backlog.admin

import com.zaneli.escalade.backlog.{ BacklogClientBase, BacklogException }
import com.zaneli.escalade.backlog.DataRetriever.{ createModel, createModels, getIntValue }
import com.zaneli.escalade.backlog.admin.model.request.AddCustomFieldParamBuilder.AddCustomFieldParam
import com.zaneli.escalade.backlog.admin.model.request.AddProjectParamBuilder.AddProjectParam
import com.zaneli.escalade.backlog.admin.model.request.AddUserParamBuilder.AddUserParam
import com.zaneli.escalade.backlog.admin.model.request.UpdateCustomFieldParamBuilder.UpdateCustomFieldParam
import com.zaneli.escalade.backlog.admin.model.request.UpdateProjectParamBuilder.UpdateProjectParam
import com.zaneli.escalade.backlog.admin.model.request.UpdateUserParamBuilder.UpdateUserParam
import com.zaneli.escalade.backlog.admin.model.response.{ Project, ProjectUser, User }
import com.zaneli.escalade.backlog.model.response.CustomFieldInfo

class BacklogAdminClient private[backlog] (spaceId: String, username: String, password: String)
  extends BacklogClientBase(spaceId, username, password) {

  @throws(classOf[BacklogException])
  def getUsers(): Array[User] = {
    val response = execute("getUsers")
    createModels(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def addUser(param: AddUserParam): User = {
    val response = execute("addUser", param)
    createModel(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def updateUser(param: UpdateUserParam): User = {
    val response = execute("updateUser", param)
    createModel(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def deleteUser(id: Int): User = {
    val response = execute("deleteUser", id)
    createModel(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def getProjects(): Array[Project] = {
    val response = execute("getProjects")
    createModels(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def addProject(param: AddProjectParam): Project = {
    val response = execute("addProject", param)
    createModel(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def updateProject(param: UpdateProjectParam): Project = {
    val response = execute("updateProject", param)
    createModel(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def deleteProject(id: Int): Project = {
    val response = execute("deleteProject", id)
    createModel(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def getProjectUsers(projectId: Int): Array[ProjectUser] = {
    val response = execute("getProjectUsers", projectId)
    createModels(response, classOf[ProjectUser])
  }

  @throws(classOf[BacklogException])
  def addProjectUser(projectId: Int, userId: Int): Array[ProjectUser] = {
    val response = execute("addProjectUser", Map(
      "project_id" -> projectId, "user_id" -> userId))
    createModels(response, classOf[ProjectUser])
  }

  @throws(classOf[BacklogException])
  def updateProjectUsers(projectId: Int, userId: Int*): Array[ProjectUser] = {
    val response = execute("updateProjectUsers", Map(
      "project_id" -> projectId, "user_id" -> userId.toArray))
    createModels(response, classOf[ProjectUser])
  }

  @throws(classOf[BacklogException])
  def deleteProjectUser(projectId: Int, userId: Int): Array[ProjectUser] = {
    val response = execute("deleteProjectUser", Map(
      "project_id" -> projectId, "user_id" -> userId))
    createModels(response, classOf[ProjectUser])
  }

  @throws(classOf[BacklogException])
  def addCustomField(param: AddCustomFieldParam): CustomFieldInfo = {
    val response = execute("addCustomField", param)
    createModel(response, classOf[CustomFieldInfo])
  }

  @throws(classOf[BacklogException])
  def updateCustomField(param: UpdateCustomFieldParam): CustomFieldInfo = {
    val response = execute("updateCustomField", param)
    createModel(response, classOf[CustomFieldInfo])
  }

  @throws(classOf[BacklogException])
  def deleteCustomField(id: Int): CustomFieldInfo = {
    val response = execute("deleteCustomField", id)
    createModel(response, classOf[CustomFieldInfo])
  }

  protected def getMethodPrefix(): String = {
    "backlog.admin."
  }
}