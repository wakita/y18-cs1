package mandelbrot1

import scala.math.{abs,max}

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.canvas.{Canvas,GraphicsContext}
import scalafx.scene.control.Button
import scalafx.scene.input.MouseEvent
import scalafx.scene.layout.{BorderPane,HBox,Priority}
import scalafx.scene.paint.Color

import complex1.Complex

/**
 * マンデルブロ顕微鏡の実装例
 *
 * 二つの複素数 -2+2i と 2+2i を対角頂点とする矩形複素領域について描画したのちに，ユーザがマウスドラッグによって指定する領域を拡大表示する．
 **/
object App extends JFXApp {
  type Region = (Complex, Complex)

  /**
   * (w, h): 描画領域の幅と高さ
   **/
  val w = 500; val h = 500

  /**
   * 描画領域
   **/
  val canvas = new Canvas(w, h)

  /**
   * history = List(c.b.a, b.a, a)
   * hisotry[hp] が表示されている．
   **/
  type History = List[Region]
  var history: History = List((new Complex(-2, -2), new Complex(2, 2)))
  var hp = 0

  var favorites: List[Region] = List()

  /**
   * complex 関数
   *
   * (x, y): (Double, Double) - 描画領域上の位置
   *
   * マウスで指定されたキャンバス上の座標(x, y)に相当する複素数を与える．
   **/
  def complex(x: Double, y: Double): Complex = {
    history(hp) match { case (c1, c2) =>
      new Complex((c1.re * (w - x) + c2.re * x) / w, (c1.im * (h - y) + c2.im * y) / h)
    }
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
  canvas.onMousePressed  = (e: MouseEvent) => p1 = complex(e.x, e.y)

  /**
   * canvas.onMouseReleased メソッド: ドラッグを終えたときに自動的に呼び出される
   *
   * ドラッグを終えた座標(e.x, e.y)に該当する複素数(p2)を得，p1 とあわせて次の描画領域(region)を設定し，再描画する．描画領域の設定にあたっては，領域の上下左右が反転しないこと，縦横比が揃うことに注意を払っている．
   **/
  canvas.onMouseReleased = { (e: MouseEvent) =>
    freshImageBehavior(history, hp, p1, complex(e.x, e.y)) match {
      case (newHistory, newHp) => update(newHistory, newHp, f"${newHistory(newHp)}")
    }
  }

  def freshImageBehavior(history: History, hp: Int, p1: Complex, p2: Complex) = {
    val size = max(abs(p2.re - p1.re), abs(p2.im - p1.im))
    val c1 = new Complex((p1.re + p2.re - size) / 2, (p1.im + p2.im - size) / 2)
    val c2 = new Complex((p1.re + p2.re + size) / 2, (p1.im + p2.im + size) / 2)
    ((c1, c2) :: history.drop(hp),
      0)
  }

  def update(newHistory: History, newHp: Int, logMessage: String) {
    history = newHistory
    hp = newHp
    println(f"$hp/${history.length}: $logMessage")
    draw(canvas.graphicsContext2D)
  }

  val quitButton = new Button("終了") { onAction = () => {
        println("終了します．")
        println(f"#お気にいり = ${favorites.length}")
        for (region <- favorites) println(region)
        System.exit(0) }
  }

  def backwardBehavior(history: History, hp: Int) = {
    if (hp < history.length) (hp + 1, true,  f"戻る: hp = $hp")
    else                     (hp,     false, "")
  }

  val backwardButton = new Button("戻る") { onAction = () => {
        backwardBehavior(history, hp) match {
          case (newHp, true, logMessage) => update(history, newHp, logMessage)
          case _ => ()
        }
      }
  }

  def forwardBehavior(hisotry: History, hp: Int) = {
    if (hp > 0) (hp - 1, true,  f"前へ: hp = $hp")
    else        (hp,     false, "")
  }

  val forwardButton = new Button("前へ") { onAction = () => {
        forwardBehavior(history, hp) match {
          case (newHp, true, logMessage) => update(history, newHp, logMessage)
          case _ => ()
        }
      }
  }

  val favoriteButton = new Button("★") { onAction = () => {
        favorites = history(hp) :: favorites
        println(f"お気に入りに登録: #favorites = ${favorites.length}")
      }
  }

  def theTitle() = f"Mandelbrot's microscope (${history(hp)})"

  stage = new PrimaryStage {
    title = theTitle()
    scene = new Scene {
      root = new BorderPane {
        hgrow = Priority.Always
        vgrow = Priority.Always
        center = canvas
        top = new HBox {
          children = List(quitButton, backwardButton, forwardButton, favoriteButton)
        }
      }
    }
  }

  draw(canvas.graphicsContext2D)

  def color(x: Int, y: Int): Color = {
    val c = complex(x, y)
    var z = new Complex(0, 0)
    var n = 0
    while (n < 255 && z.abs <= 2) {
      z = z*z + c
      n = n + 1
    }
    Color.hsb(30, 1, n / 256.0)
  }

  def draw(gc: GraphicsContext) {
    gc.clearRect(0, 0, w-1, h-1)
    for (x <- Range(0, w-1)) {
      for (y <- Range(0, h-1)) {
        color(x, y)
        gc.stroke = color(x, y)
        gc.strokeLine(x, y, x, y)
      }
    }
    stage.title = theTitle()
  }
}
