package com.zaneli.escalade.backlog.model

sealed abstract case class ColorType protected (colorCode: Int, displayValue: String) {

}

object ColorType {

  def apply(value: String): ColorType = value.toLowerCase match {
    case ColorE30000.displayValue => ColorE30000
    case Color990000.displayValue => Color990000
    case Color934981.displayValue => Color934981
    case Color814FBC.displayValue => Color814FBC
    case Color2779CA.displayValue => Color2779CA
    case Color007E9A.displayValue => Color007E9A
    case Color7EA800.displayValue => Color7EA800
    case ColorFF9200.displayValue => ColorFF9200
    case ColorFF3265.displayValue => ColorFF3265
    case Color666665.displayValue => Color666665
  }

  object ColorE30000 extends ColorType(0xe30000, "#e30000")
  object Color990000 extends ColorType(0x990000, "#990000")
  object Color934981 extends ColorType(0x934981, "#934981")
  object Color814FBC extends ColorType(0x814fbc, "#814fbc")
  object Color2779CA extends ColorType(0x2779ca, "#2779ca")
  object Color007E9A extends ColorType(0x007e9a, "#007e9a")
  object Color7EA800 extends ColorType(0x7ea800, "#7ea800")
  object ColorFF9200 extends ColorType(0xff9200, "#ff9200")
  object ColorFF3265 extends ColorType(0xff3265, "#ff3265")
  object Color666665 extends ColorType(0x666665, "#666665")
}
