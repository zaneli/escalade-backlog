package com.zaneli.escalade.backlog

import com.zaneli.escalade.backlog.model.response.ResponseModel

private object DataRetriever {

  import org.joda.time.format.DateTimeFormat
  private val dateFormatter = DateTimeFormat.forPattern("yyyyMMdd");
  private val dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");

  private[backlog] def getStringValue(map: Map[String, Any], key: String): Option[String] = {
    val data = map.get(key)
    data match {
      case Some(x: String) => Some(x.lines.map(_.trim).mkString("\n"))
      case None => None
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def getIntValue(map: Map[String, Any], key: String): Option[Int] = {
    val data = map.get(key)
    getIntValue(data)
  }

  private[backlog] def getIntValue(any: Any): Option[Int] = any match {
    case Some(x: Int) => Some(x)
    case x: Int => Some(x)
    // http://www.backlog.jp/api/method2_4.html のレスポンスXMLの例では、idがstringの場合もあるようなので特別扱いにしている。
    // (例の誤記か実際にstringで返ってくるかは不明)
    case Some(x: String) => Some(x.toInt)
    case x: String => Some(x.toInt)
    case None => None
    case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    case x => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
  }

  private[backlog] def getIntArrayValue(map: Map[String, Any], key: String): Array[Int] = {
    val data = map.get(key)
    data match {
      case Some(x: Array[String]) => x.map(_.toInt)
      case Some(x: Array[Int]) => x
      case None => Array()
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def getDoubleValue(map: Map[String, Any], key: String): Option[Double] = {
    val data = map.get(key)
    data match {
      case Some(x: Double) => Some(x)
      case None => None
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def getBooleanValue(map: Map[String, Any], key: String): Option[Boolean] = {
    val data = map.get(key)
    data match {
      case Some(x: Boolean) => Some(x)
      case None => None
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def getDateValue(map: Map[String, Any], key: String): Option[java.util.Date] = {
    val data = map.get(key)
    data match {
      case Some(x: String) if x.isEmpty() => None
      case Some(x: String) if x.size == 8 => Some(dateFormatter.parseDateTime(x).toDate)
      case Some(x: String) if x.size == 14 && x.endsWith("000000") =>
        // http://www.backlog.jp/api/method4_1.html のレスポンスXMLの例では、yyyyMMdd を期待している項目が
        // yyyyMMddHHmmss 形式で返る場合もあるようなので特別扱いにしている。
        // (例の誤記か実際に yyyyMMddHHmmss で返ってくるかは不明)
        Some(dateFormatter.parseDateTime(x.substring(0, 8)).toDate)
      case Some(x: String) if x.size == 14 => Some(dateTimeFormatter.parseDateTime(x).toDate)
      case None => None
      case Some(x: String) => throw new UnsupportedOperationException("Unexpected String Size: " + x.size)
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def getByteArrayValue(map: Map[String, Any], key: String): Array[Byte] = {
    val data = map.get(key)
    data match {
      case Some(b: Array[Byte]) => b
      case None => Array()
    }
  }

  private[backlog] def createModels[A <: ResponseModel: ClassManifest](map: Map[String, Any], key: String, model: Class[A]): Array[A] = {
    val data = map.get(key)
    data match {
      case Some(x: Object) => createModels(x, model)
      case None => Array()
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def createModels[A <: ResponseModel: ClassManifest](xs: Object, model: Class[A]): Array[A] = xs match {
    case array: Array[Object] => {
      val c = implicitly[ClassManifest[A]]
      array.map { createModel(_, model) }.toArray[A](c)
    }
  }

  private[backlog] def createModel[A <: ResponseModel](map: Map[String, Any], key: String, model: Class[A]): Option[A] = {
    val data = map.get(key)
    data match {
      case Some(x: Object) => Some(createModel(x, model))
      case None => None
      case Some(x) => throw new UnsupportedOperationException("Unexpected Type: " + x.getClass)
    }
  }

  private[backlog] def createModel[A <: ResponseModel](x: Object, model: Class[A]): A = x match {
    case map: java.util.Map[String, Any] => {
      import scala.collection.JavaConversions._
      createModelCompanion[A](model.getName, map.toMap)
    }
    case array: Array[Object] if array.size == 1 => {
      // http://www.backlog.jp/api/method_updateIssueType.html のレスポンスXMLの例では、単一要素を返すAPIで配列要素数1の配列で返る場合も
      // あるようなので要素数1の配列のみ特別扱いにしている。
      // (例の誤記か実際に配列で返ってくるかは不明)
      createModel(array.head, model)
    }
  }

  private def createModelCompanion[A <: ResponseModel](className: String, map: Map[String, Any]): A = {
    val cls = Class.forName(className + "$")
    val obj = cls.getField("MODULE$").get(null)
    val method = cls.getDeclaredMethod("apply", classOf[Map[String, Any]])
    method.setAccessible(true)
    method.invoke(obj, map).asInstanceOf[A]
  }
}