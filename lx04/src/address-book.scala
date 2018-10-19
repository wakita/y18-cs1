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

  def initial_book() = List()

  // address_book: 住所録を表現する状態変数．初期状態では住所録は空とし，空リストで表現する．
  var address_book: AddressBook = initial_book()

  def initialize() { address_book = initial_book(); }

  /**
   * 関数 lookup
   *   契約：lookup: (Name) => 登録されているときは PhoneNumber、そうでない場合は空文字列
   *
   *   例：テストケース test/address-book.scalaを参照のこと
   **/
  def _lookup(address_book: AddressBook, name: Name): Option[PhoneNumber] = {
    for ((_name, phone) <- address_book) {
      if (_name == name) return Some(phone)
    }
    return None
  }

  def lookup(name: Name): Option[PhoneNumber] = _lookup(address_book, name)

  /**
   * 関数 add
   *   契約：address_book に引数の対を追加したもの。
   *
   *   例：テストケース address-book.scalaを参照のこと
   **/

  def _add(address_book: AddressBook, name: Name, num: PhoneNumber): AddressBook =
    (name, num) :: address_book

  def add(name: Name, num: PhoneNumber) { address_book = _add(address_book, name, num) }
}
