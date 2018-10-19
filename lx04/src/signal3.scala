package signal3

// 信号機の実装例３

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

object Model {
  val Red    = 0
  val Green  = 1
  val Yellow = 2
  val colors = Array("#ff3333", "#339933", "#cccc33")

  val initial_color = Red

  def next(c: Int): Int = (c + 1) % colors.length
}

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの例
 * 信号機のシミュレータ
 **/
object App extends JFXApp {
  // いちいち Model.xxx のように書くのは面倒なので，Model のメンバーをまとめて import
  // この設定により，このコードでは initial_color, next などを Model. をつけずに参照している．
  import Model._

  // current_color: 信号機の色の違いを数値で識別
  var current_color = initial_color

  val button: Button = new Button("Next color") {
    onAction = () => {
      current_color = next(current_color)
      updateButton()
    }
  }

  def updateButton() {
    button.setStyle(f"-fx-base: ${colors(current_color)};")
  }
  updateButton()

  stage = new PrimaryStage {
    title = "Traffic Light 3"
    width = 400
    height = 400
    scene = new Scene {
      root = button
    }
  }
}
