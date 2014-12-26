package com.zaneli.xmlrpc.misc

import com.zaneli.escalade.backlog.model.FormatedDate
import com.zaneli.escalade.backlog.model.request.RequestModel
import org.apache.xmlrpc.common.{ TypeFactory, XmlRpcStreamConfig }
import org.apache.xmlrpc.parser.TypeParser
import org.apache.xmlrpc.serializer.{
  ByteArraySerializer,
  MapSerializer,
  ObjectArraySerializer,
  StringSerializer,
  TypeSerializer,
  TypeSerializerImpl
}
import org.apache.ws.commons.util.NamespaceContextImpl
import org.xml.sax.ContentHandler
import scala.collection.mutable.WrappedArray
import scala.language.reflectiveCalls
import scala.util.Try

class EscaladeTypeFactory(org: TypeFactory) extends TypeFactory {

  override def getSerializer(pConfig: XmlRpcStreamConfig, pObject: Object): TypeSerializer = {
    pObject match {
      case _: Array[Byte] => new ByteArraySerializer
      case _: Array[Int] => new IntArraySerializer(this, pConfig)
      case _: Array[_] => new ObjectArraySerializer(this, pConfig)
      case _: Map[_, _] => new ScalaMapSerializer(this, pConfig)
      case _: RequestModel => new ScalaMapSerializer(this, pConfig)
      case _: WrappedArray.ofInt => new MultiParamSerializer(this, pConfig)
      case _: WrappedArray.ofRef[_] => new MultiParamSerializer(this, pConfig)
      case _: FormatedDate => new FormatedDateSerializer
      case _ => org.getSerializer(pConfig, pObject)
    }
  }

  override def getParser(
    pConfig: XmlRpcStreamConfig, pContext: NamespaceContextImpl, pURI: String, pLocalName: String): TypeParser = {
    org.getParser(pConfig, pContext, pURI, pLocalName)
  }
}

class IntArraySerializer(pTypeFactory: TypeFactory, pConfig: XmlRpcStreamConfig)
  extends ObjectArraySerializer(pTypeFactory, pConfig) {
  override def writeData(pHandler: ContentHandler, pData: Object) {
    pData match {
      case intArray: Array[Int] => super.writeData(pHandler, intArray.map(Integer.valueOf(_)).toArray)
    }
  }
}

class ScalaMapSerializer(pTypeFactory: TypeFactory, pConfig: XmlRpcStreamConfig)
  extends MapSerializer(pTypeFactory, pConfig) {
  override def writeData(pHandler: ContentHandler, pData: Object) {
    import collection.JavaConversions._
    pData match {
      case map: Map[_, _] => super.writeData(pHandler, map: java.util.Map[_, _])
      case model: RequestModel => super.writeData(pHandler, model.params: java.util.Map[_, _])
    }
  }
}

class MultiParamSerializer(pTypeFactory: TypeFactory, pConfig: XmlRpcStreamConfig) extends TypeSerializerImpl {
  override def write(pHandler: ContentHandler, pData: Object) {
    pData match {
      case w: WrappedArray.ofInt => retrieveMultiParam(pHandler, w: _*)
      case w: WrappedArray.ofRef[_] => retrieveMultiParam(pHandler, w: _*)
    }
  }

  private def retrieveMultiParam(pHandler: ContentHandler, params: Any*) {
    val items = params.toArray match {
      case params if params.size == 1 => retrieveParam(params.head)
      case params => params.map(retrieveParam(_))
    }
    pTypeFactory.getSerializer(pConfig, items).write(pHandler, items)
  }

  private def retrieveParam(param: Any): Object = {
    param match {
      case num: Int => Integer.valueOf(num)
      case str: String => str
      case HasId(model) => Integer.valueOf(model.id)
    }
  }
}

class FormatedDateSerializer extends StringSerializer {
  override def write(pHandler: ContentHandler, pData: Object) {
    pData match {
      case date: FormatedDate => super.write(pHandler, date.format)
    }
  }
}

object HasId {
  def unapply(foo: AnyRef): Option[{ def id: Int }] = {
    if (containsMethod(foo, "id")) {
      Some(foo.asInstanceOf[{ def id: Int }])
    } else {
      None
    }
  }
  private def containsMethod(x: AnyRef, name: String, paramTypes: Class[_]*): Boolean = {
    Try(x.getClass.getMethod(name, paramTypes: _*)).isSuccess
  }
}
