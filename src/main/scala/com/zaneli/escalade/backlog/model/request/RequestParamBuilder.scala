package com.zaneli.escalade.backlog.model.request

abstract class RequestParamBuilder[A <: RequestModel] protected (params: Map[String, Any]) {

  type This

  protected def build(): A

  protected def newBuilder[B](key: String, value: Any): B = {
    newBuilder(Map(key -> value))
  }

  protected def newBuilder[B](map: Map[String, Any]): B = {
    val cls = Class.forName(this.getClass.getName)
    val constructor = cls.getDeclaredConstructor(classOf[Map[String, Any]])
    constructor.setAccessible(true)
    constructor.newInstance(params ++ map).asInstanceOf[B]
  }
}