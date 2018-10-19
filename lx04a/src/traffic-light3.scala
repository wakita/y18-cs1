package tlight3

// 信号機の実装例３

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの例
 * 信号機のシミュレータ
 **/
object TrafficLightSimulator extends JFXApp {

  val Green = 0
  val Yellow = 1
  val Red = 2
  val rgb = Array("#339933", "#cccc33", "#ff3333")

  // current_color: 各時点での信号機の色の違いを { 0, 1, 2 } で表現する状態変数．
  var current_color = Red

  def next() {
    current_color = (current_color + 1) % rgb.length
  }

  val button: Button = new Button("Next color") {
    onAction = () => {
      next()
      button.setStyle(f"-fx-base: ${rgb(current_color)};")
    }
  }
  button.fire()

  stage = new PrimaryStage {
    title = "Traffic Light"
    width = 400
    height = 400
    scene = new Scene {
      root = button
    }
  }
}
