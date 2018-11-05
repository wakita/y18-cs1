package re

import org.scalatest._

class Test extends FunSuite with Matchers {
  import RegularExpressionVM._

  val List(ca, cb, cc) = "abc".toList.map(C)

  object rexpr {
    def toStringTest() {
      def _test(r: RegularExpression, s: String) {
        val _s = r.toString
        test(f"RegularExpression.toString: ${_s}") { _s should be (s) }
      }

      _test(Empty, "")
      _test(ca, "a"); _test(cb, "b")
      _test(Concatenate(ca, cb), "(ab)")
      _test(Alternate(ca, cb), "(a|b)")
      _test(Star(ca), "(a*)")
    }

    def compileTest() {
      def _test(r: RegularExpression, label: Int, program: LProgram) {
        val s = r.toString
        test(f"RegularExpression.compile: $s") {
          val (l, p) = r._compile(0)
          l should be (label)
          p should be (program)
        }
      }

      val (pa, pb) = (ca._compile(0)._2, cb._compile(0)._2)

      _test(Empty,               0, List())
      _test(ca,                  1, List(Character('a')))
      _test(cb,                  1, List(Character('b')))
      _test(Concatenate(ca, cb), 2, pa ++ pb)
      _test(Alternate(ca, cb),   4, List(Split(1, 3)) ++ pa ++ List(Jump(4)) ++ pb)
      _test(Star(ca),            3, List(Split(1, 3)) ++ pa ++ List(Jump(0)))
    }
  }

  rexpr.toStringTest()
  rexpr.compileTest()

  class VMTest(vm: VM, vmname: String, shortName: String) {

    def runTest() {
      def accepts(r: RegularExpression, s: String) {
        test(f"$shortName: '$r' accepts '$s'") {
          val program = r.compile
          vm.execute(program, s) should be (true)
        }
      }

      def rejects(r: RegularExpression, s: String) {
        test(f"$shortName: '$r' rejects '$s'") {
          val program = r.compile
          vm.execute(program, s) should be (false)
        }
      }

      accepts(Empty, "")
      rejects(Empty, "a")
      accepts(ca, "a")
      rejects(ca, "ab")
      rejects(ca, "b")
      accepts(Concatenate(ca, cb), "ab")
      rejects(Concatenate(ca, cb), "abc")
      rejects(Concatenate(ca, cb), "bb")
      rejects(Concatenate(ca, cb), "aa")
      accepts(Alternate(ca, cb), "a")
      accepts(Alternate(ca, cb), "b")
      rejects(Alternate(ca, cb), "c")
      accepts(Star(ca), "")
      accepts(Star(ca), "a")
      accepts(Star(ca), "aa")
      accepts(Star(ca), "aaa")
      accepts(Star(Alternate(ca, cb)), "aaa")
      accepts(Star(Alternate(ca, cb)), "bbb")
      accepts(Star(Alternate(ca, cb)), "ababab")
      accepts(Star(Concatenate(ca, cb)), "ababab")
      rejects(Star(Concatenate(ca, cb)), "aaabbb")
    }

    val a = C('a')
    val optionallyA = Alternate(a, Empty)

    object TooSlow extends Exception

    def benchmark(time_limit: Double) {
      def benchmark(k: Int) {
        def ak(k: Int): RegularExpression = {
          if (k == 0) Empty
          else Concatenate(optionallyA, Concatenate(ak(k-1), a))
        }
        val rx = ak(k)
        val program = rx.compile
        val s = "a" * k
        println(f"$shortName: 'a^$k' matches '(a?)^${k}a^$k'")
        vm.execute(program, s)
      }

      try {
        for (k <- 15 to 30) {
          val t_start = System.nanoTime()
          benchmark(k)
          val t = (System.nanoTime() - t_start) / 1000000000.0
          if (t > time_limit) throw TooSlow
          println(f"$k%6d: $t%5.2fs")
        }
      } catch { case TooSlow => println("時間がかかりすぎるので、ここで打ち止め\n") }
    }

    def run() {
      println(f"Testing $vmname")
      runTest()
      println(f"Benchmarking $vmname")
      benchmark(2)
    }
  }

  new VMTest(RecursiveBacktrackingVM, "Recursive backtracking virtual machine", "REC")     .run()
  new VMTest(IterativeBacktrackingVM, "Iterative backtracking virtual machine", "ITER")    .run()
  new VMTest(KenThompsonVM,           "Ken Thompson's virtual machine",         "Thompson").run()
}
