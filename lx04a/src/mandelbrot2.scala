package mandelbrot2

import scala.math.{abs,min,max}

import scalafx.Includes._
import scalafx.application.{JFXApp,Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Scene}
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane,HBox,Priority}
import scalafx.scene.paint.Color

import complex.Complex

/**
 * マンデルブロ顕微鏡の実装例
 *
 * 二つの複素数 -2+2i と 2+2i を対角頂点とする矩形複素領域について描画したのちに，ユーザがマウスドラッグによって指定する領域を拡大表示する．
 **/
object Mandelbrot extends JFXApp {
  type Region = Array[Complex]

  /**
   * (w, h): 描画領域の幅と高さ
   **/
  val w = 500; val h = 500
  /**
   * 描画領域
   **/
  val canvas = new Canvas(w, h)

  /**
   * region: 描画中の複素平面上の矩形領域を表す対角頂点対
   **/
  var region: Region = Array(new Complex(-2, -2), new Complex(2, 2))

  var history: List[Region] = List(region)
  var hp = 0

  var favorites: List[Region] = List()

  /**
   * complex 関数
   *
   * (x, y): (Double, Double) - 描画領域上の位置
   *
   * マウスで指定されたキャンバス上の座標(x, y)に相当する複素数を与える．
   **/
  def complex(x: Double, y: Double) = {
    // val c1 = region(0); val c2 = region(1)
    val r = history(hp)
    val c1 = r(0); val c2 = r(1)
    new Complex((c1.re * (w - x) + c2.re * x) / w,
                (c1.im * (h - y) + c2.im * y) / h)
  }

  /**
   * ドラッグを開始した点が表す複素数の値
   **/
  var p1: Complex = new Complex(0, 0)
  /**
   * canvas.onMousePressed メソッド: ドラッグを開始したときに自動的に呼び出される
   *
   * ドラッグを開始した座標(e.x, e.y)に該当する複素数を求め，p1 に保存する．
   **/
  canvas.onMousePressed  = { (e: MouseEvent) =>
    p1 = complex(e.x, e.y)
  }

  /**
   * canvas.onMouseReleased メソッド: ドラッグを終えたときに自動的に呼び出される
   *
   * ドラッグを終えた座標(e.x, e.y)に該当する複素数(p2)を得，p1 とあわせて次の描画領域(region)を設定し，再描画する．描画領域の設定にあたっては，領域の上下左右が反転しないこと，縦横比が揃うことに注意を払っている．
   **/
  canvas.onMouseReleased = { (e: MouseEvent) =>
    val p2 = complex(e.x, e.y)
    val size = max(abs(p2.re - p1.re), abs(p2.im - p1.im))
    region(0) = new Complex(min(p1.re, p2.re), min(p1.im, p2.im))
    region(1) = new Complex(region(0).re + size, region(0).im + size)

    history = history.drop(hp)
    history = region :: history

    draw(canvas.graphicsContext2D)
  }

  val quitB = new Button("終了") { onAction =
      () => {
        println("終了します．")
        println(f"#favorites = ${favorites.length}")
        Platform.exit() }
  }

  val backwardB = new Button("戻る") { onAction =
      () => {
        if (hp > 0) hp = hp - 1
        println(f"戻る: hp = $hp")
        draw(canvas.graphicsContext2D)
      }
  }

  val forwardB = new Button("前へ") { onAction =
      () => {
        if (hp < history.length) hp = hp + 1
        println(f"前へ: hp = $hp")
        draw(canvas.graphicsContext2D)
      }
  }

  val favoriteB = new Button("★") { onAction =
      () => {
        favorites = history(hp) :: favorites
        println(f"お気に入りに登録: #favorites = ${favorites.length}")
      }
  }

  def theTitle() = f"Mandelbrot's microscope (${region(0).re}%f,${region(1).im}%f)-(${region(1).re}%f,${region(1).im}%f)"

  stage = new PrimaryStage {
    title = theTitle()
    scene = new Scene {
      root = new BorderPane {
        hgrow = Priority.Always
        vgrow = Priority.Always
        center = canvas
        top = new HBox {
          children = List(quitB, backwardB, forwardB, favoriteB)
        }
      }
    }
  }

  draw(canvas.graphicsContext2D)

  def draw(gc: GraphicsContext) {
    println(f"Drawing ${region(0)}-${region(1)}")
    gc.clearRect(0, 0, w-1, h-1)
    for (x <- Range(0, w-1)) {
      for (y <- Range(0, h-1)) {
        val c = complex(x, y)
        var z = new Complex(0, 0)
        var n = 0
        while (n < 255 && z.abs <= 2) {
          z = z*z + c
          n = n + 1
        }
        gc.stroke = Color.hsb(30, 1, n / 256.0)
        gc.strokeLine(x, y, x, y)
      }
    }
    stage.title = theTitle()
  }
}
