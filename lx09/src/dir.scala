package lx09

import java.io.File

object Directories {

  // ディレクトリ階層を表示する
  def df_traverse(root: File, level: Int) {
    def rec(dir: File, l: Int) {
      for (file <- dir.listFiles) {
        if (file.isDirectory) {
          println("    " * l + file.getName + "/")
          if (l < level) rec(file, l + 1)
        } else println("    " * l + file.getName)
      }
    }
    rec(root, 0)
  }

  def df_traverse(root: File, pattern: scala.util.matching.Regex) = {
    def rec(dir: File) : Option[String] = {
      val children = dir.listFiles
      for (f <- children.filter(_.isFile)) {
        pattern.findFirstIn(f.getName) match {
          case Some(name) => return Some(name)
          case None => ()
        }
      }
      children.filter(_.isDirectory).flatMap(rec).headOption
    }
    rec(root)
  }

  // デスクトップ上のファイル数をディレクトリの深さごとに列挙
  def n_files(root: File) {
    var l = 1
    var c = 0
    val queue = collection.mutable.Queue[Option[File]]()
    queue ++= List(Some(root), None)
    while (!queue.isEmpty) {
      queue.dequeue() match {
        case None => {
          if (c > 0) {
            print(f"\n$l: $c")

            queue += None
            l = l + 1; c = 0
          }
        }
        case Some(file) => {
          c = c + 1
          if (file.isDirectory) queue ++= file.listFiles.map(Some(_))
        }
      }
    }
  }

  def main(arguments: Array[String]) {
    val root = new File(sys.env("HOME") + "/Desktop")
    println(f"${root.getPath}以下にあるファイルのリスト（ただし、深さ3まで）")
    df_traverse(root, 3)

    println(f"\n${root.getPath}以下にあるファイルでファイル名が15文字以上、拡張子が3文字以上のもので最初に見つかったもの")
    println(df_traverse(root, "^[a-zA-Z0-9]{15,}\\.[a-z]{3,}$".r))

    println(f"\n${root.getPath}以下にあるファイルの個数を深さごとに集計したもの")
    n_files(root)
  }
}
