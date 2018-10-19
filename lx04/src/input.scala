import scala.io.StdIn

object Input {
  def input_loop() {
    while (true) {
      println("なにか入力して下さい。終了したいときは quit を入力して下さい。")
      StdIn.readLine().toLowerCase() match {
        case "quit" => return
        case input => println(f"入力は「$input」でした。")
      }
    }
  }

  def main(arguments: Array[String]) {
    input_loop()
  }
}
