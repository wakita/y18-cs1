/**
 * 状態変数を用いたプログラム作成にデザインレシピを利用した例
 * 住所録
 **/

object AddressBook {

  /**
   * 住所録は有限状態では表現できない．住所録は人数について実質的に宣言がないから．
   * そこで住所録の状態を表現するのにデータ構造を用いる方針とする．
   * ここでは住所録に記載された人と電話番号の対をリストで管理することとしている
   **/
  type Name = Symbol
  type PhoneNumber = String
  type ABEntry = (Name, PhoneNumber)
  type AddressBook = List[ABEntry]

  // address_book: 住所録を表現する状態変数．初期状態では住所録は空とし，空リストで表現する．
  var address_book: AddressBook = List()

  /**
   * 関数 add_to_address_book
   *   契約：add_to_address_book: (Name, PhoneNumber) => Unit
   *     引数として氏名（シンボル）と電話番号（文字列）として受け取り，返り値はなし
   *
   *   副作用：address_bookに引数の対を追加する．
   *
   *   例：テストケース lx06-address-book-test.scalaを参照のこと
   *     住所録が空のときに add_to_address_book('Adam, "1")を実行すると，住所録は List(('Adam, "1")となる
   *     住所録がList(('Eve, "2"))のときにadd_to_address_book('Adam, "1")を実行すると，住所録はList(('Adam, "1"), ('Eve, "2"))となる．
   *     住所録がList((name1, num1), (name2, num2), ...)なときにadd_to_address_book('Adam, "1")を実行すると，住所録はList(('Adam, "1"), (name1, num1), (name2, num2), ...)となる．
  **/

  def add_to_address_book(name: Name, num: PhoneNumber): Unit = {
    address_book = (name, num) :: address_book
  }
}
