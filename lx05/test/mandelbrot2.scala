package mandelbrot2

import org.scalatest._

import scala.util.Random.nextInt

class Test2 extends FunSuite with Matchers {

  import Model._

  def rClick(h: History) = complex(h, nextInt(W), nextInt(H))

  def D(h: History) = subRegion(h, rClick(h), rClick(h))
  def B(h: History) = backward(h)._1
  def F(h: History) = forward (h)._1

  def T(label: String, h: History, answer: String) {
    val size = answer.length
    test(f"$label: 履歴の長さが仕様と合致すること") {
      h._1.length should be (size)
    }

    val index = answer.indexOf('X')
    test(f"$label: 現在、表示している箇所が履歴情報の該当個所と一致すること") {
      h._2 should be (index)
    }
  }

  val h0: History = history
  T("h0",    h0,    "X")
  T("B(h0)", B(h0), "X")
  T("F(h0)", F(h0), "X")

  val hD = D(h0)
  T("hD",    hD,    "X_")
  T("F(hD)", F(hD), "X_")
  T("B(hD)", B(hD), "_X")

  val hDD = D(hD)
  T("hDD",          hDD,          "X__")
  T("F(hDD)",       F(hDD),       "X__")
  T("B(hDD)",       B(hDD),       "_X_")
  T("B(B(hDD))",    B(B(hDD)),    "__X")
  T("B(B(B(hDD)))", B(B(B(hDD))), "__X")

  val hDDB = B(hDD)
  T("hDDB",       hDDB,       "_X_")
  T("F(hDDB)",    F(hDDB),    "X__")
  T("F(F(hDDB))", F(F(hDDB)), "X__")
  T("B(hDDB)",    B(hDDB),    "__X")
  T("B(B(hDDB))", B(B(hDDB)), "__X")

  val hDDBD = D(hDDB)
  T("hDDBD",          hDDBD,          "X__")
  T("F(hDDBD)",       F(hDDBD),       "X__")
  T("B(hDDBD)",       B(hDDBD),       "_X_")
  T("B(B(hDDBD))",    B(B(hDDBD)),    "__X")
  T("B(B(B(hDDBD)))", B(B(B(hDDBD))), "__X")
}
