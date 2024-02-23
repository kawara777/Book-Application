# 最終課題
## 複数のテーブルを使用したReadの実装
### MapperとControllerのみでReadだけ実装
実行時のcurlコマンドと実行結果を以下に添付  
今回は６種類実装してみました  
※Serviceや例外処理、テストは今回は実装していません
#### 1. 全テーブルを内部結合させ全件取得
https://github.com/kawara777/Book-Application/blob/19bfe627a9318f4e711db6903674c434b2f3871a/src/main/java/com/ookawara/book_/application/Controller/BookController.java#L22-L25
```
curl --location 'http://localhost:8080/all/'
```
<details><summary>実行結果</summary><div>

  <img width="1280" alt="スクリーンショット 2024-02-22 18 14 15" src="https://github.com/kawara777/Book-Application/assets/138858245/37182655-7fc9-4833-ba17-42fb538481eb">
</div></details>

#### ２. booksテーブルのみのデータをIDで取得
https://github.com/kawara777/Book-Application/blob/bdfa57027145e64da3d7f3334bfb1010d032a7a9/src/main/java/com/ookawara/book_/application/Controller/BookController.java#L27-L30
```
curl --location 'http://localhost:8080/all/book/1'
```
<details><summary>実行結果</summary><div>

<img width="1280" alt="スクリーンショット 2024-02-22 19 36 38" src="https://github.com/kawara777/Book-Application/assets/138858245/6e6af486-566f-4f50-afa3-2dd5df6e5b5a">
</div></details>

#### 3. ctegoriesテーブルのみのデータをIDで取得
https://github.com/kawara777/Book-Application/blob/44698238b3e3861adb085e58852b8c45887fa49f/src/main/java/com/ookawara/book_/application/Controller/BookController.java#L32-L35
```
curl --location 'http://localhost:8080/all/category/1'
```
<details><summary>実行結果</summary><div>

<img width="1280" alt="スクリーンショット 2024-02-22 19 37 50" src="https://github.com/kawara777/Book-Application/assets/138858245/e5db9cd9-a288-4751-baef-04a5f13a39e4">

</div></details>

#### 4.　booksテーブルのname内で指定文字と前方一致のデータを取得
https://github.com/kawara777/Book-Application/blob/527c4a0d67a11e45a2139ae62cb70749177af0b1/src/main/java/com/ookawara/book_/application/Controller/BookController.java#L37-L40

今回は「鬼」を指定　　
※現段階で作成したデータ内で頭文字が同じデータはないためレスポンスは１件のみ
```
curl --location 'http://localhost:8080/all/names?startsWith=%E9%AC%BC'
```
<details><summary>実行結果</summary><div>

  <img width="1280" alt="スクリーンショット 2024-02-22 19 39 26" src="https://github.com/kawara777/Book-Application/assets/138858245/44469b63-27ae-496b-a79e-00450feec5f8">

</div></details>

####5. 
