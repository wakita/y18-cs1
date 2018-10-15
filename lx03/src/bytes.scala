import java.lang.{Byte => JByte}

/**
 * Byte のビット表現を観察する実験（スライドの計算機における整数の表現に対応）
 **/

object Bytes {
  implicit class StringImprovements(s: String) {
    def B: Int = JByte.parseByte(s, 2)
  }

  implicit class ByteImproviments(b: Byte) {
    def B: String = {
      f"${JByte.toUnsignedInt(b).toBinaryString}%8s"
        .map(c => if (c == ' ') '0' else c)
    }
  }

  def printBytes(bytes: Seq[Int]) {
    bytes foreach(b => println(f"$b%4d: ${b.toByte.B}%8s"))
    println()
  }

  def main(arguments: Array[String]) {
    printBytes(0 to 9)
    printBytes(119 to 127)
    printBytes(3 to -5 by -1)
    printBytes(List(1, -1, 6, -6, 100, -100))
  }
}
