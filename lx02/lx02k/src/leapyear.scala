// パス: src/leapyear.scala

object LeapYear {
  def leapyear(y: Int) : Option[Boolean] = {
    if (y < 1899) None
    else Some(!(y % 100 == 0) && y % 4 == 0)
  }
}
