import org.scalatest._

import AddressBook._

class AddressBookTest extends FunSuite {

  val initial_book = AddressBook.initial_book()
  val adam     = ('Adam, "014-1421-356")
  val eve      = ('Eve,  "017-3205-08")
  val new_adam = ('Adam, "Adam's new #phone")

  def add(address_book: AddressBook, data: (Name, PhoneNumber)): AddressBook =
    AddressBook._add(address_book, data._1, data._2)

  test("住所録の初期状態は空です") {
    assert(initial_book === List())
  }

  test("空の住所録にデータを追加したときに、住所録はそのデータを含むこと。") {
    val address_book = add(initial_book, adam)
    assert(_lookup(address_book, adam._1) === Some(adam._2))
  }

  test("連続してふたつのデータを登録したら、どちらも登録されていること。") {
    val address_book = add(add(initial_book, adam), eve)
    assert(_lookup(address_book, adam._1) === Some(adam._2))
    assert(_lookup(address_book, eve._1)  === Some(eve._2))
  }

  test("登録した情報を検索できること") {
    val adam_added = add(initial_book, adam)
    assert(_lookup(adam_added, adam._1) === Some(adam._2))

    val eve_also_added = add(adam_added, eve)
    assert(_lookup(eve_also_added, adam._1) === Some(adam._2))
    assert(_lookup(eve_also_added, eve._1)  === Some(eve._2))
  }

  test("登録を更新したとき、検索結果が最新の情報であること") {
    assert(_lookup(add(add(initial_book, adam), new_adam), 'Adam) === Some(new_adam._2))
  }
}
