package mandelbrot2

import org.scalatest._

import scala.util.Random.nextInt

class Test3 extends FunSuite with Matchers {

  import Model._

  def rClick(h: History) = complex(h, nextInt(W), nextInt(H))

  type Spec = (String, History)

  def D(spec: Spec) = {
    val h = spec._2
    (f"D(${spec._1})", subRegion(h, rClick(h), rClick(h)))
  }

  def B(spec: Spec) = {
    val h = spec._2
    (f"B(${spec._1})", backward(h)._1)
  }

  def F(spec: Spec) = {
    val h = spec._2
    (f"F(${spec._1})", forward (h)._1)
  }

  def T(spec: Spec, answer: String) {
    val label = spec._1
    val h = spec._2

    val size = answer.length
    test(f"$label: 履歴の長さが仕様と合致すること") {
      h._1.length should be (size)
    }

    val index = answer.indexOf('X')
    test(f"$label: 現在、表示している箇所が履歴情報の該当個所と一致すること") {
      h._2 should be (index)
    }
  }

  val spec0: Spec = (".", history)
  T(spec0, "X")
  T(B(spec0), "X")
  T(F(spec0), "X")

  val specD: Spec = D(spec0)
  T(specD,       "X_")
  T(F(specD),    "X_")
  T(B(specD),    "_X")
  T(B(B(specD)), "_X")

  val specDD: Spec = D(specD)
  T(specDD,          "X__")
  T(F(specDD),       "X__")
  T(B(specDD),       "_X_")
  T(B(B(specDD)),    "__X")
  T(B(B(B(specDD))), "__X")

  val specDDB: Spec = B(specDD)
  T(F(specDDB),      "X__")
  T(F(F(specDDB)),   "X__")

  val specDDBD: Spec = D(specDDB)
  T(specDDBD,          "X__")
  T(F(specDDBD),       "X__")
  T(B(specDDBD),       "_X_")
  T(B(B(specDDBD)),    "__X")
  T(B(B(B(specDDBD))), "__X")
}
