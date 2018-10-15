/*
 * sbtでの実行方法:
 *   project lx03
 *   runMain Constants
 **/

import java.lang.{Long => JLong, Float => JFloat, Double => JDouble}
import java.lang.{Float => JFloat}
import java.lang.{Double => JDouble}
import java.lang.AssertionError

object Constants {
  def main(arguments: Array[String]) {
    print(f"BYTES:        ${JDouble.BYTES}\n")
    print(f"SIZE:         ${JDouble.SIZE}\n")
    print(f"MAX_VALUE:    ${JDouble.MAX_VALUE}\n")
    print(f"MIN_NORMAL:   ${JDouble.MIN_NORMAL}\n")
    print(f"MIN_VALUE:    ${JDouble.MIN_VALUE}\n")
    print(f"MAX_EXPONENT: ${JDouble.MAX_EXPONENT}\n")
    print(f"MIN_EXPONENT: ${JDouble.MIN_EXPONENT}\n")
  }
}

trait Improvements {
  implicit class StringImprovements(s: String) {
    def I: Int = Integer.parseInt(s, 2)
    def L: Long = JLong.parseLong(s, 2)
  }

  implicit class IntImprovements(x: Int) {
    def B: String = x.toBinaryString
  }

  implicit class DoubleImprovements(x: Double) {
    // val S = 1; val E = 11; val F = 52
    def longBits : Long = JDouble.doubleToLongBits(x)
    def B: String = x.longBits.toBinaryString
  }
}

object Lib {
  def bitsToLong(bits: Array[Int]) : Long = {
    assert(bits.length <= JDouble.SIZE)
    bits.foldLeft(0L) { (lbits, b) => (lbits << 1) + (if (b == 0) 0 else 1) }
  }

  def bitsToDouble(bits: Array[Int]) : Double =
    JDouble.longBitsToDouble(bitsToLong(bits))
}

object DoubleLab {
  import Lib._

  val S = 1  // 符号部(sign)のビット数
  val E = 11 // 指数部(exponent)のビット数
  val F = 52 // 仮数部(fractional)のビット数
  val dE = 1023L // 指数部の差分

  val MIN_E = JDouble.MIN_EXPONENT + (1 << (E-1))
  val MAX_E = JDouble.MAX_EXPONENT + (1 << (E-1))

  assert(S + E + F == JDouble.SIZE)

  def format(s: Long, e: Long, f: Long) : Double =
    JDouble.longBitsToDouble((s << E + e) << F + f)

  def format(s: Long, e: Long, f: Array[Int]) : Double =
    format(s, e, bitsToLong(f))

  def format(s: Long, e: Array[Int], f: Array[Int]) : Double = {
    assert(e.length <= E)
    assert(f.length <= F)
    format(s, bitsToLong(e), bitsToLong(f))
  }
}
