// パス: src/leapyear.scala

object LeapYear {
  def leapyear(y: Int) : Boolean = {
    !(y % 100 == 0) && y % 4 == 0
  }
}
