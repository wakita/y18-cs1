package signal2

// 信号機の実装例２

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

trait TrafficSignal {
  val next: TrafficSignal
  val rgb:  String
}

object Green extends TrafficSignal {
  val next = Yellow
  val rgb  = "#339933"
}

object Yellow extends TrafficSignal {
  val next = Red
  val rgb  = "#cccc33"
}

object Red extends TrafficSignal {
  val next = Green
  val rgb  = "#ff3333"
}

object Model {
  val initial_signal: TrafficSignal = Red

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
  
  def next(light: TrafficSignal): TrafficSignal = light.next
}

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの例
 * 信号機のシミュレータ
 **/
object App extends JFXApp {
  // 状態変数 current_signal: 信号機の色を表現する状態変数．
  var current_signal: TrafficSignal = Model.initial_signal

  val button: Button = new Button("Next color") {
    onAction = () => {
      current_signal = Model.next(current_signal)
      updateButton()
    }
  }

  def updateButton() {
      button.setStyle(f"-fx-base: ${current_signal.rgb};")
  }
  updateButton()

  stage = new PrimaryStage {
    title = "Traffic Light 2"
    width = 400
    height = 400
    scene = new Scene {
      root = button
    }
  }
}
