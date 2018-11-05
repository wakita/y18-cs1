package re

/**
 * Scala の正規表現機能についての実験
 * a?a?..a?aa..a のような正規表現の aaa のような文字列への適用に要する時間の測定
 **/

object Benchmark {
  def benchmark(pattern: scala.util.matching.Regex, string: String): Double = {
      val time_start = System.nanoTime()
      pattern.findFirstIn(string)
      (System.nanoTime() - time_start) / 1000000000.0
  }

  def main(arguments: Array[String]) {
    var i = 1
    var cont = true
    while (cont) {
      val t = benchmark(("a?" * i + "a" * i).r, "a" * i)
      println(f"$i%2d: $t%5.2fs")
      if (t > 10) cont = false
      i = i + 1
    }
  }
}

/**
ベンチマークの実行結果

実行環境
    Python 3.5.2 |Anaconda 4.2.0 (x86_64)| (default, Jul  2 2016, 17:52:12)
    [GCC 4.2.1 Compatible Apple LLVM 4.2 (clang-425.0.28)] on darwin

    MacBook Pro (Retina, 15-inch, Mid 2014 model
    2.5 GHz Intel Core i7, 16GB 1600 MHz DDR3)

 1:  0.00s
 2:  0.00s
 3:  0.00s
 4:  0.00s
 5:  0.00s
 6:  0.00s
 7:  0.00s
 8:  0.00s
 9:  0.00s
10:  0.00s
11:  0.00s
12:  0.00s
13:  0.00s
14:  0.00s
15:  0.00s
16:  0.00s
17:  0.00s
18:  0.01s
19:  0.01s
20:  0.02s
21:  0.05s
22:  0.09s
23:  0.19s
24:  0.35s
25:  0.78s
26:  1.51s
27:  3.21s
28:  6.19s
29: 13.35s
 **/
