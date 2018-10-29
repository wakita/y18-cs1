object Main {
  // 愚直な定義の方法
  def findFirstInt(key: Int, seq: List[Int]): Option[Int] = {
    def aux(i: Int, seq: List[Int]): Option[Int] = {
      seq match {
        case Nil => None
        case x :: _ if x == key => Some(i)
        case _ :: rest => aux(i + 1, rest)
      }
    }

    println(f"findFirstInt")
    aux(0, seq)
  }

  def findFirstString(key: String, seq: List[String]): Option[Int] = {
    def aux(i: Int, seq: List[String]): Option[Int] = {
      seq match {
        case Nil => None
        case x :: _ if x == key => Some(i)
        case _ :: rest => aux(i + 1, rest)
      }
    }

    println("findFirstString")
    aux(0, seq)
  }

  // Method override: メソッドの多重定義（同名だが引数の型が異なるものを複数定義すること）
  def findFirst(key: Int, seq: List[Int]): Option[Int] = {
    def aux(i: Int, seq: List[Int]): Option[Int] = {
      seq match {
        case Nil => None
        case x :: _ if x == key => Some(i)
        case _ :: rest => aux(i + 1, rest)
      }
    }

    println("findFirst: (Int, List[Int]) -> Option[Int]")
    aux(0, seq)
  }

  def findFirst(key: String, seq: List[String]): Option[Int] = {
    def aux(i: Int, seq: List[String]): Option[Int] = {
      seq match {
        case Nil => None
        case x :: _ if x == key => Some(i)
        case _ :: rest => aux(i + 1, rest)
      }
    }

    println("findFirst: (String, List[String]) -> Option[Int]")
    aux(0, seq)
  }


  // 多相的な定義
  def find[A](key: A, seq: List[A]): Option[Int] = {
    def aux(i: Int, seq: List[A]): Option[Int] = {
      seq match {
        case Nil => None
        case x :: _ if x == key => Some(i)
        case _ :: rest => aux(i + 1, rest)
      }
    }

    println("find[A]: (A, List[A]) -> Option[Int]")
    aux(0, seq)
  }

  def main(arguments: Array[String]) {
    val iseq = List(3, 1, 4, 1, 5, 9)
    val sseq = List("Hello", "to", "Scala", "world")

    println(f"    (5, $iseq) => ${findFirstInt(5, iseq)}")
    println(f"    (Scala, $sseq) => ${findFirstString("Scala", sseq)}")
    println(f"    (5, iseq) => ${findFirst(5, iseq)}")
    println(f"    (Scala, $sseq) => ${findFirst("Scala", sseq)}")

    val dseq = List(1.41421, 2.23606, 2.64575, 3.14159)

    println(f"    (5, $iseq) => ${find(5, iseq)}")
    println(f"    (Scala, $sseq) => ${find("Scala", sseq)}")
    println(f"    (2.64575, $dseq) => ${find(2.64575, dseq)}")
  }
}
