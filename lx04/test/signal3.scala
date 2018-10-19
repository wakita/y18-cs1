package signal3

import org.scalatest._
import Model._

class Test extends FunSuite {

  test("信号機の初期状態で信号は赤") {
    assert(initial_color === Red)
  }

  test("次は青") {
    assert(next(initial_color) === Green)
  }

  test("次の次は黄") {
    assert(next(next(initial_color)) === Yellow)
  }

  // 有限状態変数しかないので状態が一巡して赤に戻れば，それ以上のテストは不要
  test("次の次の次は赤") {
    assert(next(next(next(initial_color))) === Red)
  }
}
