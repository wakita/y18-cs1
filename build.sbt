// ライブラリ群

// テストのために使う ScalaTest ライブラリ
// http://www.scalatest.org/user_guide/using_scalatest_with_sbt
val scalatest = Seq("org.scalactic" %% "scalactic" % "3.0.5",
                    "org.scalatest" %% "scalatest" % "3.0.5" % "test")

// GUI 記述のための ScalaFX ライブラリ
// http://www.scalafx.org/docs/quickstart/
val scala_fx = "org.scalafx" %% "scalafx" % "8.0.144-R12"

// 以下の設定は脇田がスライドを作成するために sbt のなかから make コマンドを実行するため
import scala.sys.process._
lazy val make = taskKey[Unit]("Build with the Makefile")

lazy val common = Seq(
  organization := "prg18.is.titech.ac.jp",
  version := "0.1.0-SNAPSHOT",

  // scalac に与えるオプション
  scalaVersion := "2.12.6",   // コンパイルに使う scalac のバージョン
  scalacOptions := Seq("-feature", "-unchecked", "-deprecation",
    "-Ywarn-dead-code", "-Ywarn-unused", "-Ywarn-unused-import"),

  libraryDependencies ++= scalatest,

  fork in Test := true,
  fork in run := true,
  connectInput := true,

  // sbt はログ出力をバッファする。以下の設定を施すことで、test についてはこの標準設定を解除する。
  // ※ 「バッファする」メモリに溜め込むこと
  logBuffered in Test := false,

  // Scalaのプログラムの置き場所を非標準の場所に指定．通常はこのような設定はせずにデフォルトの src/scala/*.scala, test/scala/*.scala に置きます．通常の設定ではJavaとの共存のためにsrcとtestの下に scala と java のためのディレクトリを用意しているのですが，本実習ではJavaのプログラムを書くことはないので，非標準ですが単純な構成を施しています．
  // https://www.scala-sbt.org/1.x/docs/Multi-Project.html
  scalaSource in Compile := baseDirectory.value / "src",
  scalaSource in Test    := baseDirectory.value / "test",

  make := { "make" ! }
)

lazy val common_fx = common ++ Seq(libraryDependencies ++= Seq(scala_fx))

// サブプロジェクト群の定義．このように定めることで，共通の `build.sbt` でたくさんのプロジェクトの設定を一括して施しています．
lazy val root = (project in file(".")).settings(common)

lazy val lx01 = (project in file("lx01")).settings(common)
lazy val lx01a_sbt = (project).settings(common)

val lx02 = "lx02/lx02"
lazy val lx02a = (project in file(lx02 + "a")).settings(common)
lazy val lx02b = (project in file(lx02 + "b")).settings(common)
lazy val lx02c = (project in file(lx02 + "c")).settings(common)
lazy val lx02d = (project in file(lx02 + "d")).settings(common)
lazy val lx02e = (project in file(lx02 + "e")).settings(common)
lazy val lx02f = (project in file(lx02 + "f")).settings(common)
lazy val lx02g = (project in file(lx02 + "g")).settings(common)
lazy val lx02h = (project in file(lx02 + "h")).settings(common)
lazy val lx02i = (project in file(lx02 + "i")).settings(common)
lazy val lx02j = (project in file(lx02 + "j")).settings(common)
lazy val lx02k = (project in file(lx02 + "k")).settings(common)

/*
lazy val lx03 = (project in file("lx03")).settings(common)
lazy val lx04 = (project in file("lx04")).settings(common_fx)
lazy val lx05 = (project in file("lx05")).settings(common_fx)
lazy val lx06 = (project in file("lx06")).settings(common_fx)
lazy val lx07 = (project in file("lx07")).settings(common_fx)
lazy val lx08 = (project in file("lx08")).settings(common_fx)
lazy val lx09 = (project in file("lx09")).settings(common)
lazy val lx10 = (project in file("lx09")).settings(common)
lazy val lx11 = (project in file("lx11")).settings(common)
lazy val lx12 = (project in file("lx12")).settings(common)
*/
