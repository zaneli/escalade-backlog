package com.zaneli.escalade.backlog.model

class FormatedDate(date: java.util.Date, pattern: String) {
  def format(): String = {
    import org.joda.time.DateTime
    val dateTime = new DateTime(date);
    dateTime.toString(pattern)
  }
}