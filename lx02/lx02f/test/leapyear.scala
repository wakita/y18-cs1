// test/leapyear.scala

import org.scalatest._
import LeapYear._

class LeapYearTest extends FlatSpec {
  "4で割り切れる年" should "閏年である" in {
    assert(leapyear(2012) == (true))
    assert(leapyear(2016) == (true))
  }

  "4で割り切れない年" should "閏年でない" in {
    assert(leapyear(2014) == (false))
    assert(leapyear(2015) == (false))
    assert(leapyear(2017) == (false))
  }
}
