package tlight2

// 信号機の実装例２

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

trait TrafficLight {
  val next: TrafficLight
  val rgb: String
}

object Green extends TrafficLight {
  val next = Yellow
  val rgb  = "#339933"
}

object Yellow extends TrafficLight {
  val next = Red
  val rgb  = "#cccc33"
}

object Red extends TrafficLight {
  val next = Green
  val rgb  = "#00cc00"
}

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの例
 * 信号機のシミュレータ
 **/
object TrafficLightSimulator extends JFXApp {

  // current_color: 各時点での信号機の色を表現する状態変数．赤，緑，黄の3状態を取る．初期状態は黄信号
  var current_color: TrafficLight = Red

  /**
   * 関数 next
   *   契約: next: Unit => Unit
   *   入出力の働き: この関数は入出力を伴わない
   *   副作用: current_color を以下の要領で更新する．
   *       緑 → 黄，黄 → 赤，赤 → 緑
   *
   *   例: テストケース traffic-light2.scala を参照のこと
   *       current_colorがGreenのときにnext()を実行するとcurrent_colorは更新されてYellowとなる．
   *       current_colorがYellowのときにnext()を実行するとcurrent_colorは更新されてRedとなる．
   *       current_colorがRedのときにnext()を実行するとcurrent_colorは更新されてGreenとなる．
   **/
  
  def next() {
    current_color = current_color.next
  }

  val button: Button = new Button("Next color") {
    onAction = () => {
      next()
      button.setStyle(f"-fx-base: ${current_color.rgb};")
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
