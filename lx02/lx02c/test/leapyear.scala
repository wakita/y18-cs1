// test/leapyear.scala

import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }
}
