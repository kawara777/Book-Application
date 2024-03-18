package com.ookawara.book.application.mapper;

import com.ookawara.book.application.entity.Book;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    void 全ての本のデータを返す() {
        List<Book> books = bookMapper.findAll();
        assertThat(books)
                .hasSize(3)
                .containsOnly(
                        new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                        new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                        new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));
    }

    @Test
    @Sql(scripts = {"classpath:/sqlannotation/delete-categories.sql", "classpath:/sqlannotation/delete-books.sql"})
    @Transactional
    void レコードがないとき空のデータを返す() {
        List<Book> books = bookMapper.findAll();
        assertThat(books).isEmpty();
    }
}
