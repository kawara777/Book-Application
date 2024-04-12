package com.ookawara.book.application.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.MyBatisSystemException;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookMapperTest {

    @Autowired
    BookMapper bookMapper;

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void すべての本のデータが取得できること() {
        List<Book> books = bookMapper.findAll();
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/delete-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void レコードが存在しない時に空のデータが返されること() {
        List<Book> books = bookMapper.findAll();
        assertThat(books).isEmpty();
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 指定した文字列を含むカテゴリーに該当する全ての本のデータを取得() {
        List<Book> books = bookMapper.findBy("ノ", "", null);
        assertThat(books)
                .hasSize(1)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル")
                );
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 指定した文字列を含む書籍名に該当する全ての本のデータを取得() {
        List<Book> books = bookMapper.findBy("", "の", null);
        assertThat(books)
                .hasSize(2)
                .containsOnly(
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 購入済みの全ての本のデータを取得() {
        List<Book> books = bookMapper.findBy("", "", true);
        assertThat(books)
                .hasSize(2)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 未購入の全ての本のデータを取得() {
        List<Book> books = bookMapper.findBy("", "", false);
        assertThat(books)
                .hasSize(1)
                .containsOnly(
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画")
                );
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/insert-categories.sql",
                    "classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/insert-books.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 全てにnullを指定したときsql文の条件により全ての本のデータを返すこと() {
        List<Book> books = bookMapper.findBy(null, null, null);
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void カテゴリーに空文字を書籍名と購入状況にnullを指定したときsql文の条件により全ての本のデータを返すこと() {
        List<Book> books = bookMapper.findBy("", null, null);
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 書籍名に空文字をカテゴリーと購入状況にnullを指定したときsql文の条件により全ての本のデータを返すこと() {
        List<Book> books = bookMapper.findBy(null, "", null);
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 書籍名とカテゴリーに空文字を購入状況にnullを指定したときsql文の条件により全ての本のデータを返すこと() {
        List<Book> books = bookMapper.findBy("", "", null);
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説")
                );
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void カテゴリーに存在しない文字列を指定したとき空のデータを返すこと() {
        List<Book> books = bookMapper.findBy(" ", "", null);
        assertThat(books).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 書籍名に存在しない文字列を指定したとき空のデータを返すこと() {
        List<Book> books = bookMapper.findBy("", " ", null);
        assertThat(books).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在する本のIDを指定したときに正常に本のデータを返す() {
        Optional<Book> book = bookMapper.findByBookId(1);
        assertThat(book)
                .contains(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル")
                );
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在しない本のIDを指定した時に空のデータを返す() {
        Optional<Book> book = bookMapper.findByBookId(0);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在するカテゴリーのIDを指定したときに正常にカテゴリーのデータを返す() {
        Optional<Category> category = bookMapper.findByCategoryId(1);
        assertThat(category)
                .contains(new Category(1, "漫画"));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在しないカテゴリーのIDを指定した時に空のデータを返す() {
        Optional<Category> category = bookMapper.findByCategoryId(0);
        assertThat(category).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 指定した全てのレコードが完全一致した本のデータを返す() {
        Optional<Book> book = bookMapper.findBook("鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1);
        assertThat(book).contains(new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 指定した全てのレコードが一致しないとき空のデータを返す() {
        Optional<Book> book = bookMapper.findBook("", LocalDate.of(2000, 1, 1), null, 0);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 書籍名のみ指定したとき空のデータを返す() {
        Optional<Book> book = bookMapper.findBook("鬼滅の刃・1", null, null, 0);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 発売日のみ指定したとき空のデータを返す() {
        Optional<Book> book = bookMapper.findBook(" ", LocalDate.of(2016, 6, 8), null, 0);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 購入状況のみ指定したとき空のデータを返す() {
        Optional<Book> book = bookMapper.findBook(null, null, false, 0);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void カテゴリーIDのみ指定したとき空のデータを返す() {
        Optional<Book> book = bookMapper.findBook(" ", null, null, 1);
        assertThat(book).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void カテゴリーに指定した文字列が完全一致したデータを返す() {
        Optional<Category> category = bookMapper.findCategory("小説");
        assertThat(category).contains(new Category(3, "小説"));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void カテゴリーに指定した文字列が一致しないとき空のデータを返す() {
        Optional<Category> category = bookMapper.findCategory("しょうせつ");
        assertThat(category).isEmpty();
    }

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-books.sql", "classpath:/sqlannotation/reset-book-id.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void 本の情報と新しく採番されたIDが正常に登録されること() {
        Book book = new Book("鬼滅の刃・2", LocalDate.of(2016, 8, 9), false, 1);
        bookMapper.insertBook(book);

        List<Book> books = bookMapper.findAll();
        assertThat(books)
                .hasSize(1)
                .containsOnly(
                        new Book(1, "鬼滅の刃・2", LocalDate.of(2016, 8, 9), false, 1, "漫画"));
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/create/create-categories.yml", ignoreCols = "category_id")
    @Transactional
    void カテゴリーと新しく採番されたIDが正常に登録されること() {
        Category category = new Category("エッセイ");
        bookMapper.insertCategory(category);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-allColumn.yml")
    @Transactional
    void IDで指定した書籍のID以外全て更新できること() {
        Book book = new Book(2, "鬼滅の刃 1", LocalDate.of(2016, 7, 8), true, 2);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-name.yml")
    @Transactional
    void IDで指定した書籍の名前のみが更新できること() {
        Book book = new Book(2, "鬼滅の刃 1", null, null, null);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-releaseDate.yml")
    @Transactional
    void IDで指定した書籍の発売日のみが更新できること() {
        Book book = new Book(2, null, LocalDate.of(2016, 7, 8), null, null);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-isPurchased.yml")
    @Transactional
    void IDで指定した書籍の購入履歴のみが更新できること() {
        Book book = new Book(2, "", null, true, null);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-categoryId.yml")
    @Transactional
    void IDで指定した書籍のカテゴリーIDのみが更新できること() {
        Book book = new Book(2, " ", null, null, 2);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void IDで指定した書籍のカテゴリーIDに0以下を指定したときに例外がスローされること() {
        Book book = new Book(2, " ", null, null, 0);
        assertThatThrownBy(() -> bookMapper.updateBook(book))
                .hasRootCause(new IllegalArgumentException("1以上の整数を入力してください。"));
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet("datasets/books.yml")
    @Transactional
    void IDで指定した書籍のデータと同じ値を更新するときデータが更新されないこと() {
        Book book = new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1);
        bookMapper.updateBook(book);
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet("datasets/books.yml")
    @Transactional
    void IDで指定した書籍が存在しないときデータが更新されないこと() {
        Book book = new Book(4, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1);
        bookMapper.updateBook(book);
    }
}
