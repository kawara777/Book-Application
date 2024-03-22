package com.ookawara.book.application.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.ookawara.book.application.entity.Book;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 指定した文字列を含むデータが存在しないとき空のデータを返す() {
        List<Book> books = bookMapper.findBy(null, " ", null);
        assertThat(books).isEmpty();
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在する本のIDを指定したときに正常に本のデータを返す () {
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
}
