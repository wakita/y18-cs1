package signal4

// 信号機の実装例４

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Ellipse

object Model {
  val colors = Array(Color.Red, Color.Green, Color.Yellow)
  val initial_color = 0
  def next(s: Int): Int = (s + 1) % colors.length
}

/**
 * 有限状態を保存した状態変数を用いたプログラムのデザインレシピの例
 * 信号機のシミュレータ
 **/
object App extends JFXApp {
  import Model._

  // current_color: 信号機の色の違いを { 0, 1, 2 } で表現する
  var current_color = initial_color

  def ellipse(i: Int) = new Ellipse {
    fill = Color.White
    centerX = 150 * i + 100; centerY = 100
    radiusX = 60; radiusY = 60
  }

  val signals = List(0, 1, 2).map(i => ellipse(i))
  val drawingPane = new Pane {
    minWidth = 450; minHeight = 200
    children = signals
  }

  def updateSignals() {
    signals.foreach(signal => signal.fill = Color.LightGray)
    signals(current_color).fill = colors(current_color)
  }
  updateSignals()

  val button: Button = new Button("Next color") {
    onAction = () => {
      current_color = next(current_color)
      updateSignals()
    }
  }

  stage = new PrimaryStage {
    title = "Traffic Light 4"
    scene = new Scene {
      root = new BorderPane {
        right = button
        center = drawingPane
      }
    }
  }
}
