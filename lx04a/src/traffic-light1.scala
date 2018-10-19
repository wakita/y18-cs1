package tlight1

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの適用例
 * 信号機のシミュレータ
 **/
object TrafficLightSimulator extends JFXApp {

  abstract class Color // 信号機を表す型：信号機の色として可能性のある場合を列挙したもの
  case class Red()    extends Color // 信号が赤に点灯している状態を表す値（初期状態）
  case class Green()  extends Color // 信号が緑に点灯している状態を表す値
  case class Yellow() extends Color // 信号が黄に点灯している状態を表す値

  // current_color: 各時点での信号機の色を表現する状態変数．赤，緑，黄の3状態を取る．初期状態は赤信号
  var current_color: Color = Red()

  /**
   * 関数 next
   *   契約: next: Unit => Unit
   *   入出力の働き: この関数は入出力を伴わない
   *   副作用: current_color を以下の要領で更新する．
   *       緑 → 黄，黄 → 赤，赤 → 緑
   *
   *   例: テストケースtraffic-light1.scalaを参照のこと
   *       current_color が Green()  のときに next() を実行すると current_color は更新されて Yellow() となる．
   *       current_color が Yellow() のときに next() を実行すると current_color は更新されて Red() となる．
   *       current_color が Red()    のときに next() を実行すると current_color は更新されて Green() となる．
   **/
  
  def next(): Unit = {
    current_color =
      current_color match {
        case Green()  => Yellow()
        case Yellow() => Red()
        case Red()    => Green()
      }

      /*
      上は以下と同等の内容について，三つの代入をひとつにすっきりとまとめている

      current_color match {
        case Green()  => current_color = Yellow()
        case Yellow() => current_color = Red()
        case Red()    => current_color = Green()
      }
      */
  }

  val button: Button = new Button("Next color") {
    onAction = () => {
      next()
      val c = current_color match {
        case Green()  => "339933"
        case Yellow() => "cccc33"
        case Red()    => "ff3333"
      }
      button.setStyle(f"-fx-base: #$c;")
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
