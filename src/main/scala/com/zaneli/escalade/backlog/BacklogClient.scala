package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.DataRetriever.{ createModel, createModels, getIntValue }
import com.zaneli.escalade.backlog.model.ColorType
import com.zaneli.escalade.backlog.model.request.AddVersionParamBuilder.AddVersionParam
import com.zaneli.escalade.backlog.model.request.CountIssueParamBuilder.CountIssueParam
import com.zaneli.escalade.backlog.model.request.CreateIssueParamBuilder.CreateIssueParam
import com.zaneli.escalade.backlog.model.request.GetCustomFieldParamBuilder.GetCustomFieldParam
import com.zaneli.escalade.backlog.model.request.FindIssueParamBuilder.FindIssueParam
import com.zaneli.escalade.backlog.model.request.SwitchStatusParamBuilder.SwitchStatusParam
import com.zaneli.escalade.backlog.model.request.UpdateIssueParamBuilder.UpdateIssueParam
import com.zaneli.escalade.backlog.model.request.UpdateVersionParamBuilder.UpdateVersionParam
import com.zaneli.escalade.backlog.model.response.{
  ActivityType,
  Comment,
  Component,
  CustomFieldInfo,
  Issue,
  IssueType,
  Priority,
  Project,
  ProjectSummary,
  Resolution,
  ResponseModel,
  Status,
  Timeline,
  User,
  UserIcon,
  Version
}

class BacklogClient private[backlog] (spaceId: String, username: String, password: String)
  extends BacklogClientBase(spaceId, username, password) {

  @throws(classOf[BacklogException])
  def getProjects(): Array[Project] = {
    val response = execute("getProjects")
    createModels(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def getProject(projectKey: String): Project = {
    val response = execute("getProject", projectKey)
    createModel(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def getProject(projectId: Int): Project = {
    val response = execute("getProject", projectId)
    createModel(response, classOf[Project])
  }

  @throws(classOf[BacklogException])
  def getComponents(projectId: Int): Array[Component] = {
    val response = execute("getComponents", projectId)
    createModels(response, classOf[Component])
  }

  @throws(classOf[BacklogException])
  def getVersions(projectId: Int): Array[Version] = {
    val response = execute("getVersions", projectId)
    createModels(response, classOf[Version])
  }

  @throws(classOf[BacklogException])
  def getUsers(projectId: Int): Array[User] = {
    val response = execute("getUsers", projectId)
    createModels(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def getIssueTypes(projectId: Int): Array[IssueType] = {
    val response = execute("getIssueTypes", projectId)
    createModels(response, classOf[IssueType])
  }

  @throws(classOf[BacklogException])
  def getIssue(issueKey: String): Issue = {
    val response = execute("getIssue", issueKey)
    createModel(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def getIssue(issueId: Int): Issue = {
    val response = execute("getIssue", issueId)
    createModel(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def getComments(issueId: Int): Array[Comment] = {
    val response = execute("getComments", issueId)
    createModels(response, classOf[Comment])
  }

  @throws(classOf[BacklogException])
  def countIssue(param: CountIssueParam): Int = {
    val response = execute("countIssue", param)
    getIntValue(response).getOrElse(0)
  }

  @throws(classOf[BacklogException])
  def findIssue(param: FindIssueParam): Array[Issue] = {
    val response = execute("findIssue", param)
    createModels(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def createIssue(param: CreateIssueParam): Issue = {
    val response = execute("createIssue", param)
    createModel(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def updateIssue(param: UpdateIssueParam): Issue = {
    val response = execute("updateIssue", param)
    createModel(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def switchStatus(param: SwitchStatusParam): Issue = {
    val response = execute("switchStatus", param)
    createModel(response, classOf[Issue])
  }

  @throws(classOf[BacklogException])
  def addComment(key: String, content: String): Comment = {
    val response = execute("addComment", Map("key" -> key, "content" -> content))
    createModel(response, classOf[Comment])
  }

  @throws(classOf[BacklogException])
  def addIssueType(projectId: Int, name: String, color: ColorType): IssueType = {
    val response = execute("addIssueType",
      Map("project_id" -> projectId, "name" -> name, "color" -> color.displayValue))
    createModel(response, classOf[IssueType])
  }

  @throws(classOf[BacklogException])
  def updateIssueType(id: Int, name: String, color: ColorType): IssueType = {
    val response = execute("updateIssueType",
      Map("id" -> id, "name" -> name, "color" -> color.displayValue))
    createModel(response, classOf[IssueType])
  }

  @throws(classOf[BacklogException])
  def deleteIssueType(id: Int, substituteId: Option[Int] = None): IssueType = {
    val param = substituteId match {
      case Some(x) => Map("id" -> id, "substitute_id" -> Integer.valueOf(x))
      case None => Map("id" -> id)
    }
    val response = execute("deleteIssueType", param)
    createModel(response, classOf[IssueType])
  }

  @throws(classOf[BacklogException])
  def addVersion(param: AddVersionParam): Version = {
    val response = execute("addVersion", param)
    createModel(response, classOf[Version])
  }

  @throws(classOf[BacklogException])
  def updateVersion(param: UpdateVersionParam): Version = {
    val response = execute("updateVersion", param)
    createModel(response, classOf[Version])
  }

  @throws(classOf[BacklogException])
  def deleteVersion(id: Int): Version = {
    val response = execute("deleteVersion", id)
    createModel(response, classOf[Version])
  }

  @throws(classOf[BacklogException])
  def addComponent(projectId: Int, name: String): Component = {
    val response = execute("addComponent", Map("project_id" -> projectId, "name" -> name))
    createModel(response, classOf[Component])
  }

  @throws(classOf[BacklogException])
  def updateComponent(id: Int, name: String): Component = {
    val response = execute("updateComponent", Map("id" -> id, "name" -> name))
    createModel(response, classOf[Component])
  }

  @throws(classOf[BacklogException])
  def deleteComponent(id: Int): Component = {
    val response = execute("deleteComponent", id)
    createModel(response, classOf[Component])
  }

  @throws(classOf[BacklogException])
  def getTimeline(): Array[Timeline] = {
    val response = execute("getTimeline")
    createModels(response, classOf[Timeline])
  }

  @throws(classOf[BacklogException])
  def getProjectSummary(projectId: Int): ProjectSummary = {
    val response = execute("getProjectSummary", projectId)
    createModel(response, classOf[ProjectSummary])
  }

  @throws(classOf[BacklogException])
  def getProjectSummaries(): Array[ProjectSummary] = {
    val response = execute("getProjectSummaries")
    createModels(response, classOf[ProjectSummary])
  }

  @throws(classOf[BacklogException])
  def getUser(userId: Int): User = {
    val response = execute("getUser", userId)
    createModel(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def getUser(loginId: String): User = {
    val response = execute("getUser", loginId)
    createModel(response, classOf[User])
  }

  @throws(classOf[BacklogException])
  def getUserIcon(userId: Int): UserIcon = {
    val response = execute("getUserIcon", userId)
    createModel(response, classOf[UserIcon])
  }

  @throws(classOf[BacklogException])
  def getUserIcon(loginId: String): UserIcon = {
    val response = execute("getUserIcon", loginId)
    createModel(response, classOf[UserIcon])
  }

  @throws(classOf[BacklogException])
  def getActivityTypes(): Array[ActivityType] = {
    val response = execute("getActivityTypes")
    createModels(response, classOf[ActivityType])
  }

  @throws(classOf[BacklogException])
  def getStatuses(): Array[Status] = {
    val response = execute("getStatuses")
    createModels(response, classOf[Status])
  }

  @throws(classOf[BacklogException])
  def getResolutions(): Array[Resolution] = {
    val response = execute("getResolutions")
    createModels(response, classOf[Resolution])
  }

  @throws(classOf[BacklogException])
  def getPriorities(): Array[Priority] = {
    val response = execute("getPriorities")
    createModels(response, classOf[Priority])
  }

  @throws(classOf[BacklogException])
  def getCustomFields(param: GetCustomFieldParam): Array[CustomFieldInfo] = {
    val response = execute("getCustomFields", param)
    createModels(response, classOf[CustomFieldInfo])
  }

  protected def getMethodPrefix(): String = {
    "backlog."
  }
}