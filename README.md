# 最終課題
## 複数のテーブルを使用したReadの実装
### MapperとControllerのみでReadだけ実装
実行時のcurlコマンドと実行結果を以下に添付  
今回は６種類実装してみました  
※Serviceや例外処理、テストは今回は実装していません
### 1. 全テーブルを内部結合させ全件取得
```
curl --location 'http://localhost:8080/all/'
```
<details><summary>実行結果</summary><div>

  <img width="1280" alt="スクリーンショット 2024-02-22 18 14 15" src="https://github.com/kawara777/Book-Application/assets/138858245/37182655-7fc9-4833-ba17-42fb538481eb">
</div></details>
