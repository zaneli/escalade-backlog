package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.request.RequestModel
import org.apache.xmlrpc.client.{ XmlRpcClient, XmlRpcClientConfigImpl }
import com.zaneli.xmlrpc.misc.EscaladeTypeFactory

abstract class BacklogClientBase protected (spaceId: String, username: String, password: String) {

  private val clientConfig = new XmlRpcClientConfigImpl();

  try {
    clientConfig.setServerURL(new java.net.URL("https://" + spaceId + ".backlog.jp/XML-RPC"))
  } catch {
    case e: java.net.MalformedURLException =>
      throw new BacklogException(e)
  }

  clientConfig.setBasicUserName(username)
  clientConfig.setBasicPassword(password)

  private val client = new XmlRpcClient()
  client.setTypeFactory(new EscaladeTypeFactory(client.getTypeFactory()))
  client.setConfig(clientConfig)

  protected def execute(method: String, params: Any*): Object = try {
    import scala.collection.JavaConversions._
    client.execute(getMethodPrefix() + method, params)
  } catch {
    case e: org.apache.xmlrpc.XmlRpcException =>
      throw new BacklogException(e)
  }

  protected def execute(method: String, model: RequestModel): Object = {
    executeByMap(method, model.params)
  }

  protected def execute(method: String, params: Map[String, Any]): Object = {
    executeByMap(method, params)
  }

  protected def getMethodPrefix(): String

  private def executeByMap(method: String, params: Map[String, Any]): Object = {
    try {
      client.execute(getMethodPrefix() + method, Array[Object](params))
    } catch {
      case e: org.apache.xmlrpc.XmlRpcException =>
        throw new BacklogException(e)
    }
  }
}