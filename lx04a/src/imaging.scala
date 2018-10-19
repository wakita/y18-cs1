package imaging

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Group,Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.shape.ArcType

object ImagingDemo extends JFXApp {
  val w = 800
  val h = 600
  val canvas = new Canvas(w, h)

  stage = new PrimaryStage {
    title = "Imaging Demo"
    width = w
    height = h
    scene = new Scene {
      fill = LightGray
      root = new Group(canvas)
    }
  }

  draw(canvas.graphicsContext2D)

  def draw(gc: GraphicsContext) {
    gc.fill   = Green
    gc.stroke = Blue
    gc.lineWidth = 5

    gc.strokeLine(40, 10, 10, 40)

    gc.fillOval(10, 60, 30, 30)
    gc.strokeOval(60, 60, 30, 30)

    gc.fillRoundRect(110, 60, 30, 30, 10, 10)
    gc.strokeRoundRect(160, 60, 30, 30, 10, 10)

    gc.fillArc( 10, 110, 30, 30, 45, 240, ArcType.Open)
    gc.fillArc( 60, 110, 30, 30, 45, 240, ArcType.Chord)
    gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.Round)

    gc.strokeArc( 10, 160, 30, 30, 45, 240, ArcType.Open)
    gc.strokeArc( 60, 160, 30, 30, 45, 240, ArcType.Chord)
    gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.Round)

    gc.fillPolygon(Array(10, 40, 10, 40), Array(210, 210, 240, 240), 4)
    gc.strokePolygon(Array(60, 90, 60, 90), Array(210, 210, 240, 240), 4)
    gc.strokePolyline(Array(110, 140, 110, 140), Array(210, 210, 240, 240), 4)

    gc.lineWidth = 1
    for (hue <- Range(0, 359)) {
      gc.stroke = Color.hsb(hue, 1.0,  1.0)
      gc.strokeLine(10 + hue, 320, 10 + hue, 330)
    }

    for (brightness <- Range(0, 359)) {
      gc.stroke = Color.hsb(0, 1.0, brightness / 360.0)
      gc.strokeLine(10 + brightness, 335, 10 + brightness, 345)
    }

    for (brightness <- Range(0, 359)) {
      gc.stroke = Color.hsb(0, 0.0, brightness / 360.0)
      gc.strokeLine(10 + brightness, 350, 10 + brightness, 360)
    }

    for (hue <- Range(0, 359)) {
      for (brightness <- Range(0, 359)) {
        val x = 400 + hue
        val y = 10 + brightness
        gc.stroke = Color.hsb(hue, 1.0, brightness / 360.0)
        gc.strokeLine(x, y, x, y)
      }
    }
  }
}
