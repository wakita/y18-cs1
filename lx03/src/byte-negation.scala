/**
 * ビット演算（ここではビット反転 ~）と加算を用いた符号反転の計算
 *
 * sbtでの実行方法:
 *   project lx03
 *   runMain ByteNegation
 **/

object ByteNegation {
  def main(arguments: Array[String]) {
    val b5: Byte = 5
    println("-x を算術演算子の - を使わずに，~x+1 で計算する実験")
    println(f"-$b5 を ${~b5+1} ")
  }
}
