import scala.collection.mutable.ArraySeq
import scala.util.Random
import scala.io.StdIn

object Hangman {
  type Word = Set[Char]

  val r = Random

  val dictionary : ArraySeq[Word] = "The world famous consulting detective Sherlock Holmes lived at 221b Baker Street between 1881-1904, according to the stories written by Sir Arthur Conan Doyle. Mr Holmes shared his rooms with his loyal friend and colleague Dr Watson. The house is protected by the government due to its \"special architectural and historical interest\" and the 1st floor study overlooking Baker Street is still faithfully maintained for posterity as it was kept in Victorian Times.".filterNot("01489-,.\"".toSet).split(" ").map(word => word.toLowerCase.toSet)

  val bodyParts : List[String] = "頭、胴体、左腕、右腕、左脚、右脚".split("、").toList

  // ３つの状態変数: chosenWord, knownLetters, bodyPartsLeft

  // 親がゲームのために選択した単語．子の目標はこの単語に含まれる文字をすべて言い当てること．
  var chosenWord: Word = dictionary(r.nextInt(dictionary.length))

  // 親が選んだ単語について子がすでに得ている知識
  var knownLetters: Word = Set()

  // まだ描画されずに残っている体躯の部分
  var bodyPartsLeft: List[String] = bodyParts

  // テストのために用意した初期構成．
  def initialize() {
    chosenWord    = dictionary(r.nextInt(dictionary.length))
    knownLetters  = Set()
    bodyPartsLeft = bodyParts
  }

  // 純関数的記述：画面出力，状態変数の更新をしていないことに注意．
  def hangmanCheck(correctAnswer: Word, knowledge: Word, bodyPartsLeft: List[String], c: Char) = {
    val newKnowledge   = (knowledge + c).intersect(correctAnswer)
    val youWon         = newKnowledge == correctAnswer
    val badGuess       = newKnowledge.size == knowledge.size
    val gameOver       = badGuess && bodyPartsLeft.length == 1
    val shouldContinue = !(youWon || gameOver)
    val message        =
      (if (youWon) f"あなたの勝ちです！正解は${correctAnswer}でした。"
        else if (!badGuess) "あたってますよ。"
        else if (gameOver) f"ゲームオーバーです。残念でした。正解は${correctAnswer}です。"
        else f"はずれ。「${bodyPartsLeft.head}」を絞首台に描いちゃいましょ。")
    (newKnowledge, message, badGuess, shouldContinue)
  }

  // 副作用の記述：画面出力，状態変数の更新などをひとまとめに
  def hangmanGuess(c: Char): Boolean = {
    println(f"あなたの予想: $c")
    hangmanCheck(chosenWord, knownLetters, bodyPartsLeft, c) match {
      case (knownLetters2, message, badGuess, shouldContinue) => {
        knownLetters = knownLetters2
        println(f"あなたが既にあてた文字: $knownLetters")
        println(message)
        if (badGuess) bodyPartsLeft = bodyPartsLeft.tail
        shouldContinue
      }
    }
  }

  def inputChar() = {
    println(); println("私が選んだ単語にどんな文字が含まれているか、一文字だけ予想して下さい。")
    StdIn.readChar()
  }

  def main(arguments: Array[String]) {
    initialize()
    do {} while (hangmanGuess(inputChar()))
  }
}
