// パス: src/leapyear.scala

object LeapYear {
  def leapyear(y: Int) : Option[Boolean] = {
    Some(!(y % 100 == 0) && y % 4 == 0)
  }
}
