package lx09

import scala.collection.mutable.{ArrayStack => Stack, Queue}

object Traverse {

  // 木構造の定義
  case class Tree[T](value: T, children: List[Tree[T]])

  // 木を作成するためのお助け関数（かなり面倒だったので。。。）
  def N[T](value: T, children: Tree[T]*) = Tree(value, children.toList)

  // 例題の木
  val t = N(1, N(2, N(3, N(4), N(5)),
                    N(6)),
               N(7),
               N(8, N(9, N(10), N(11)),
                    N(12)))

  // 再帰的に記述した深さ優先探索 (depth-first traversal)
  def dftraverse_rec[T](t: Tree[T]) : List[T] = {
    def aux[T](t: Tree[T], l: List[T]): List[T] = {
      t.children.foldLeft(l)((l: List[T], t: Tree[T]) => aux(t, t.value :: l))
    }
    aux(t, List(t.value)).reverse
  }

  // 逐次的に記述した深さ優先探索
  def dftraverse[T](t: Tree[T]) : List[T] = {
    val stack = new Stack[Tree[T]]()
    var values = List[T]()
    stack.push(t)
    while (!stack.isEmpty) {
      val t = stack.pop()
      values = t.value :: values
      t.children.reverse.foreach(stack.push(_))
    }
    values.reverse
  }

  // 幅優先探索
  def bftraverse[T](t: Tree[T]) : List[T] = {
    val queue = new Queue[Tree[T]]()
    var values = List[T]()
    queue.enqueue(t)
    while (!queue.isEmpty) {
      val t = queue.dequeue()
      values = t.value :: values
      t.children.foreach(queue.enqueue(_))
    }
    values.reverse
  }

  // 多相的かつ汎用的な探索アルゴリズムの一般的な記述
  trait TraverseStrategy[T] {
    def isEmpty: Boolean
    def add(t: Tree[T])
    def remove(): Tree[T]
    var values = List[T]()
    def visit(t: Tree[T]) { t.children.foreach(add(_)) }

    def traverse(t: Tree[T]) : List[T] = {
      add(t)
      while (!isEmpty) {
        val t = remove()
        values = t.value :: values
        visit(t)
      }
      values.reverse
    }
  }
  
  // 汎用的な探索を特殊化して幅優先探索を実装
  class BF_Traverse[T] extends Queue[Tree[T]] with TraverseStrategy[T] {
    def add(t: Tree[T]) { enqueue(t) }
    def remove() = { dequeue() }
  }

  // 汎用的な探索を特殊化して深さ優先探索を実装
  class DF_Traverse[T] extends Stack[Tree[T]] with TraverseStrategy[T] {
    def add(t: Tree[T]) { push(t) }
    def remove() = { pop() }
    override def visit(t: Tree[T]) { t.children.reverse.foreach(add(_)) }
  }

  def main(arguments: Array[String]) {
    println("dftraverse_rec", dftraverse_rec(t))
    println("dftraverse", dftraverse(t))
    println("bftraverse", bftraverse(t))

    // 汎用的な記述の特殊化によるもの
    println(new BF_Traverse[Int]().traverse(t))
    println(new DF_Traverse[Int]().traverse(t))
  }
}
