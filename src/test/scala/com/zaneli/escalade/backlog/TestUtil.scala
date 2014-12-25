package com.zaneli.escalade.backlog

import com.github.nscala_time.time.Imports.DateTimeFormat
import com.zaneli.escalade.backlog.admin.BacklogAdminClient

import java.io.{ ByteArrayOutputStream, OutputStream }

import org.apache.xmlrpc.client.XmlRpcClient
import org.specs2.mock.Mockito

import scala.language.reflectiveCalls
import scala.xml.{ Elem, XML }

trait TestUtil extends Mockito {

  private val dateFormat = DateTimeFormat.forPattern("yyyyMMdd")
  private val dateTimeFormat = DateTimeFormat.forPattern("yyyyMMddHHmmss")

  def getClient(fileName: String, out: OutputStream): BacklogClient = {
    val client = new BacklogClient("", "", "")
    setMock(client, fileName, out)
    client
  }

  def getAdminClient(fileName: String, out: OutputStream): BacklogAdminClient = {
    val client = new BacklogAdminClient("", "", "")
    setMock(client, fileName, out)
    client
  }

  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B) = try {
    f(resource)
  } finally {
    resource.close()
  }

  def getActualRequest(out: ByteArrayOutputStream): Elem = {
    XML.loadString(new String(out.toByteArray, "UTF-8"))
  }

  def getExpectedRequest(fileName: String): Elem = {
    using(this.getClass.getResourceAsStream("input/" + fileName)) {
      XML.load(_)
    }
  }

  def getDate(dateStr: String): java.util.Date = {
    dateStr match {
      case _ if dateStr.length == 8 => dateFormat.parseDateTime(dateStr).toDate()
      case _ if dateStr.length == 14 => dateTimeFormat.parseDateTime(dateStr).toDate()
    }
  }

  def getImgFileData(): Array[Byte] = {
    using(classOf[TestUtil].getResourceAsStream("logo_mark.png")) { in =>
      return Stream.continually(in.read).takeWhile(_ != -1).map(_.toByte).toArray
    }
  }

  private def setMock(client: BacklogClientBase, fileName: String, out: OutputStream) {
    import org.apache.xmlrpc.client.{
      XmlRpcClientWorker,
      XmlRpcClientWorkerFactory,
      XmlRpcSunHttpTransportFactory
    }

    val mockXmlRpcClient = spy(new XmlRpcClient)
    val mockWorkerFactory = spy(new XmlRpcClientWorkerFactory(mockXmlRpcClient))
    val mockWorker = spy(new XmlRpcClientWorker(mockWorkerFactory))
    val mockTransportFactory = spy(new XmlRpcSunHttpTransportFactory(mockXmlRpcClient))

    mockXmlRpcClient.setTypeFactory(new com.zaneli.xmlrpc.misc.EscaladeTypeFactory(mockXmlRpcClient.getTypeFactory()))
    mockXmlRpcClient.getMaxThreads returns 1
    mockXmlRpcClient.getWorkerFactory returns mockWorkerFactory
    mockXmlRpcClient.getTransportFactory returns mockTransportFactory
    mockWorkerFactory.getWorker returns mockWorker
    mockTransportFactory.getTransport() returns new MockXmlRpcSunHttpTransport(
      mockXmlRpcClient, this.getClass.getResourceAsStream("output/" + fileName), out)

    val clientClass = classOf[BacklogClientBase]
    val clientField = clientClass.getDeclaredField("client")
    clientField.setAccessible(true)
    clientField.set(client, mockXmlRpcClient)
  }
}

import java.io.InputStream
import java.net.{ URL, URLConnection, HttpURLConnection }
import org.apache.xmlrpc.client.XmlRpcSunHttpTransport
class MockXmlRpcSunHttpTransport(client: XmlRpcClient, in: InputStream, out: OutputStream) extends XmlRpcSunHttpTransport(client) {

  var con: MockURLConnection = null;

  override def newURLConnection(pURL: URL): URLConnection = {
    con = new MockURLConnection(pURL, out)
    con
  }
  override def getURLConnection(): URLConnection = con
  override def getInputStream(): InputStream = in
}

class MockURLConnection(url: URL, out: OutputStream) extends HttpURLConnection(url) {
  override def connect() {}
  override def disconnect() {}
  override def usingProxy(): Boolean = false
  override def getOutputStream(): OutputStream = out
}
