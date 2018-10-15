/**
 * 2の羃の計算結果から数学の世界の整数と計算機上の整数型の違いを学びます
 **/

object PowersA {
  // 2^nを Byte で計算する関数
  def powerOf2B(n: Byte): Byte = {
    def aux(i: Int, p: Byte): Byte = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, (p+p).toByte)
    }

    println("\n2^n[Byte]")
    aux(0, 1.toByte)
  }

  // 2^nを Short で計算する関数
  def powerOf2S(n: Short): Short = {
    def aux(i: Int, p: Short): Short = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, (p+p).toShort)
    }

    println("\n2^n[Short]")
    aux(0, 1.toShort)
  }

  // 2^nを Int 上で計算する関数
  def powerOf2I(n: Int): Int = {
    def aux(i: Int, p: Int): Int = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, p+p)
    }

    println("\n2^n[Int]")
    aux(0, 1.toInt)
  }

  // 2^nを Long で計算する関数
  def powerOf2L(n: Long): Long = {
    def aux(i: Int, p: Long): Long = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, p+p)
    }

    println("\n2^n[Long]")
    aux(0, 1.toLong)
  }

  /*
   * sbtでの実行方法:
   *   project lx03
   *   run-main PowersA
   **/

  def main(arguments: Array[String]): Unit = {
    powerOf2B(1.toByte)
    powerOf2S(1.toShort)
    powerOf2I(1.toInt)
    powerOf2L(1.toLong)
  }
}

object PowersB {
  // 2^nを Byte で計算する関数
  def powerOf2(n: Byte): Byte = {
    def aux(i: Int, p: Byte): Byte = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, (p + p).toByte)
    }

    println("\n2^n[Byte]")
    aux(0, 1.toByte)
  }

  // 2^nを Short で計算する関数
  def powerOf2(n: Short): Short = {
    def aux(i: Int, p: Short): Short = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, (p + p).toShort)
    }

    println("\n2^n[Short]")
    aux(0, 1.toShort)
  }

  // 2^nを Int 上で計算する関数
  def powerOf2(n: Int): Int = {
    def aux(i: Int, p: Int): Int = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, p + p)
    }

    println("\n2^n[Int]")
    aux(0, 1.toInt)
  }

  // 2^nを Long で計算する関数
  def powerOf2(n: Long): Long = {
    def aux(i: Int, p: Long): Long = {
      println(f"2^${i} = ${p}")
      if (p == 0) p
      else aux(i + 1, p + p)
    }

    println("\n2^n[Long]")
    aux(0, 1.toLong)
  }

  /*
   * sbtでの実行方法:
   *   project lx03
   *   run-main PowersB
   **/

  def main(arguments: Array[String]): Unit = {
    powerOf2(1.toByte)
    powerOf2(1.toShort)
    powerOf2(1.toInt)
    powerOf2(1.toLong)
  }
}
