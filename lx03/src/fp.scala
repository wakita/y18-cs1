/**
  * 浮動小数点数演算におけるオーバーフローやアンダーフローの観察
  */

object FPOverflow {
 /*
  * sbtでの実行方法:
  *   project lx03
  *   runMain FPOverflow
  */
  def main(arguments: Array[String]) {
    def addMany(delta: Float, n: Int, v: Float): Float =
      if (n == 0) v else addMany(delta, n-1, v + delta)

    println(f"0.01 * 100 = ${addMany(0.01f, 100, 0f)}")

    val N = 5000000
    val s0 = 0f

    val s1 = addMany(1f, N, s0)
    println(f"1.0 * $N = $s1\n")

    val s2 = addMany(1f, N, s1)
    println(f"1.0 * $N = $s2\n")

    val s3 = addMany(1f, N, s2)
    println(f"1.0 * $N = $s3\n")

    val s4 = addMany(1f, N, s3)
    println(f"1.0 * $N = $s4\n")

    val s5 = addMany(1f, N, s4)
    println(f"1.0 * $N = $s5\n")
  }
}

object FPUnderflow {
 /*
  * sbtでの実行方法:
  *   project lx03
  *   runMain FPUnderflow
  */
  def fpUnderflow(n: Int, f: Double) {
    def aux(i: Int, x: Double) {
      if (i != n) {
        println(f"$f * 2^-$i = $x%g")
        aux(i+1, x/2)
      }
    }
    aux(0, f)
  }

  def main(arguments: Array[String]): Unit = {
    fpUnderflow((2<<9) + 52, 1.0)
  }

  /**
    * さて，fpUnderflowのパラメタの式はあまりに恣意的に見える．
    * だが，ここで (2<<9) + 52 を与えたことで，プログラムは最初に 0.00000 が出現したところで終了する．
    * コードの著者はこのパラメタをどうやって予想したのだろう．
    * IEEE 754標準との関連で考えて，この疑問に答えてみよう．
    */
}
