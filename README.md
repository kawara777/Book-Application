# 書籍管理API
## 概要
書籍とそのカテゴリーに関するCRUD機能をもった REST API です。
## 主な使用技術
- Java
- Spring Boot
- MyBatis
- MySQL
- Docker
- 自動テスト
- CI（Git Actions、自動テストを実装）
- AWSデプロイ（これから実施予定）
## アプリケーション概略図

## 機能
以下の処理を実行できます。（categories に対してはこれから実装予定）
- 全件検索
- ID検索
- 項目検索（単一、複数 可）
- 登録
- 更新（単一、複数 可）
- 削除
## 設計書
## E-R図
![book-application drawio (1)](https://github.com/kawara777/book-application/assets/138858245/746a8845-4e1f-4958-a43c-6e88baaa6226)
## データベース定義
### books
| カラム名（論理名） | カラム名（物理名） | 型（桁）      | Nullable | その他                                           | 
| :----------------: | :----------------: | :-----------: | :------: | :----------------------------------------------: | 
| ID                 | book_id            | int           | NO       | PRIMARY KEY<br>AUTO INCREMENT                    | 
| 書籍名             | name               | VARCHAR(1000) | NO       |                                                  | 
| 発売日             | release_date       | DATE          | NO       | yyyy-MM-dd 形式                                  | 
| 購入状況           | is_Purchased       | TINYINT(1)    | NO       |                                                  | 
| カテゴリーID       | category_id        | int           | NO       | FOREIGN KEY<br>DELETE SET NULL<br>UPDATE CASCADE | 
### categories
| カラム名（論理名） | カラム名（物理名） | 型（桁）    | Nullable | その他 | 
| :----------------: | :----------------: | :---------: | :------: | :----: | 
| ID                 | category_id        | int         | NO       | PRIMARY KEY       | 
| カテゴリー         | category           | VARCHAR(20) | NO       |        | 
## API仕様書

## ローカルでのアプリケーション起動方法

## 自動テスト
