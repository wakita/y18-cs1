package signal1

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button

abstract class Color // 信号機を表す型：信号機の色として可能性のある場合を列挙したもの
case class Red()    extends Color // 信号が赤に点灯している状態を表す値（初期状態）
case class Green()  extends Color // 信号が緑に点灯している状態を表す値
case class Yellow() extends Color // 信号が黄に点灯している状態を表す値

object Model {
  // current_color: 信号機の色を表現する状態変数．赤，緑，黄の3状態を取る．初期状態は赤信号
  val initial_color: Color = Red()

  /**
   * 関数 next
   *   契約: next: Unit => Unit
   *   入出力の働き: この関数は入出力を伴わない
   *   副作用: current_color を以下の要領で更新する．
   *       緑 → 黄，黄 → 赤，赤 → 緑
   *
   *   例: テストケースtraffic-light1.scalaを参照のこと
   *       current_color が Green()  のときに next() を実行すると Yellow() となる．
   *       current_color が Yellow() のときに next() を実行すると Red()    となる．
   *       current_color が Red()    のときに next() を実行すると Green()  となる．
   **/
  
  def next(c: Color): Color = {
    c match {
      case Green()  => Yellow()
      case Yellow() => Red()
      case Red()    => Green()
    }
  }
}

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの適用例
 * 信号機のシミュレータ
 **/
object App extends JFXApp {
  // 状態変数 current_light: 信号機の色を表現する状態変数．
  var current_color: Color = Model.initial_color

  val button: Button = new Button("Next color") {
    onAction = () => {
      current_color = Model.next(current_color)
      updateButton()
    }
  }

  // 状態変数 current_light に応じて，画面を更新する手続き
  // これは初期化のときと画面をクリックしたときに呼び出される．
  def updateButton() {
    val c = current_color match {
      case Green()  => "339933"
      case Yellow() => "cccc33"
      case Red()    => "ff3333"
    }
    button.setStyle(f"-fx-base: #$c;")
  }
  updateButton()

  stage = new PrimaryStage {
    title = "Traffic Light 1"
    width = 400
    height = 400
    scene = new Scene {
      root = button
    }
  }
}
