import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}

object Fib {
  // Future の計算終了を待って結果を得る例 (Await.result(f, t))
  def future(): Unit = {
    val s = "Hello"
    val f: Future[String] = Future { List(s, " future!").reduce((s1, s2) => s1 + s2) }

    println(f"$s + ...: ${Await.result(f, Duration.Inf)}")
  }

  // Future を合成する例 (for { v1 <- f1 v2 <- f2 } yield (...))
  def add() {
    val f1: Future[Int] = Future { 1 }
    val f2: Future[Int] = Future { 2 }

    val sum: Future[Int] = for {
      v1 <- f1
      v2 <- f2
    } yield (v1 + v2)

    println(f"1 + 2 = ${Await.result(sum, Duration.Inf)}")
  }

  // Future を合成する例 (f1.zip(f2))
  def add_zip() {
    val f1: Future[Int] = Future { 1 }
    val f2: Future[Int] = Future { 2 }

    val (v1, v2) = Await.result(f1.zip(f2), Duration.Inf)

    println(f"1 + 2 = ${v1 + v2}")
  }

  // Promise を介して結果を得る例 (p success ...)
  def promise(): Unit = {
    val s = "Hello"

    val p = Promise[String]()
    Future { p success List(s, " future!").reduce((s1, s2) => s1 + s2) }

    println(f"Value from promise: ${Await.result(p.future, Duration.Inf)}")
  }

  // Promise を利用して Future を合成する例
  def add_promise() {
    def zip(p1: Promise[Int], p2: Promise[Int]): Future[Int] = {
      for {
        v1 <- p1.future
        v2 <- p2.future
      } yield (v1 + v2)
    }

    val p1 = Promise[Int]()
    Future { p1 success 1 }

    val p2 = Promise[Int]()
    Future { p2 success 2 }

    println(f"1 + 2 = ${Await.result(zip(p1, p2), Duration.Inf)}")
  }

  trait Fibonacci {
    def fib(n: Int): Int
  }

  object recursive extends Fibonacci {
    def fib(n: Int): Int = if (n <= 1) 1 else fib(n-2) + fib(n-1)
  }

  object parallel extends Fibonacci {

    // promise1, promise2 はそれぞれ fib(n-1), fib(n-2) を計算している Future たちと繋がる Promise
    // これらの結果を収集したのちに promise_sum で待っている Future に合計値を伝える。
    def sum(promise1: Promise[Int], promise2: Promise[Int], promise_sum: Promise[Int]) = {
      val (v1, v2) = Await.result(promise1.future.zip(promise2.future), Duration.Inf)
      promise_sum success (v1 + v2)
    }

    // Fibonacci(n)を計算した結果を p に伝える
    def fib(n: Int, p: Promise[Int]) {
      if (n <= 1) p success 1
      else {
        // fib(n-1), fib(n-2)を計算するFutureとPromise
        val promise1 = Promise[Int]()
        val promise2 = Promise[Int]()
        Future { fib(n-1, promise1) }
        Future { fib(n-2, promise2) }

        // fibを計算するFutureたちから結果を受け取りその合計をpで待つFutureに伝える
        sum(promise1, promise2, p)
      }
    }

    def fib(n: Int) = {
      val p = Promise[Int]()
      fib(n, p)
      Await.result(p.future, Duration.Inf)
    }
  }

  def usage() {
      println("runMain Fib {future, add, promise, add_promise, rec, fib}")
  }

  def main(arguments: Array[String]) {
    val param = if (arguments.length == 0) "recursive" else arguments(0)
    param match {
      case "future" => future()
      case "rec" => println(f"Fibonacci(10) = ${recursive.fib(10)}")
      case "promise" => promise()
      case "add" => add()
      case "add_zip" => add_zip()
      case "add_promise" => add_promise()
      case "fib" => {
        println(f"fib(10) = ${recursive.fib(10)}")
        println(f"future.fib(10) = ${parallel.fib(10)}")
      }
      case _ => usage()
    }
  }
}
