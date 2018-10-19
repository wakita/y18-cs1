import scala.io.StdIn
import scala.util.Random

object ColorQuiz {
  val debug = true
  val endless = false

  val colornames = ("黒,白,赤,青,緑,金,桃,橙,紫,藍").split(",").toList
  val colorchoice =
    colornames.zip(Range(0, colornames.length))
      .map((ci: (String, Int)) => ci match { case (c, i) => f"$i:$c" })
      .mkString(", ")

  type Answer = List[Int]

  // 状態変数
  var correctAnswer: Answer = List(0, 0)
  var trial = 0

  def master() {
    correctAnswer = List.fill(2)(Random.nextInt(colornames.length))
    trial = 0
  }

  def do_check(ca: Answer, a: Answer): (String, Boolean) = {
    if (a == ca) ("おみごと！", true)
    else {
      (if (a(0) == ca(0) || a(1) == ca(1)) "一箇所正解"
        else if (a(0) == ca(1) || a(1) == ca(0)) "いずれかの色を使ってます"
        else "大はずれ",
        false)
    }
  }

  def master_check(answer: List[Int]): Boolean = {
    answer match {
      case List(_, _) => {
        do_check(correctAnswer, answer) match {
          case (message, finished) =>
            print(f"${trial}回目の試行: $message")
            finished
        }
      }
      case l => {
        print(f"${l.length}個入力なさいましたね。ちょうど2個入力をお願いします。")
        false
      }
    }
  }

  /**
   * 入力を受け取り，コンマや空白を区切り文字として扱う方法のサンプル
   *
   * 実行時にエラーが出る場合は Evernote の「注意」を参照のこと
   */
  def input() : List[Int] = {
    println("以下から2色を選んで入力して下さい．")
    println(colorchoice)

    // 空白またはコンマ [ ,] の連続 [ ,]+ を区切りとして入力行を分割
    // "正規表現の記述".r というように正規表現を文字列で記述し .r を付加することで正規表現が得られる
    val input = "[ ,]+".r.split(StdIn.readLine()).toList.map(s => s.toInt)
    input match {
      case List(_, _) => input
      case _ => List()
    }
  }

  def main(arguments: Array[String]) {
    master()
    do { do { trial += 1 } while (!(master_check(input())))
    } while (endless)
  }
}
