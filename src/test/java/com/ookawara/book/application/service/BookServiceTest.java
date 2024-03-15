package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.exception.BookNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookMapper bookMapper;

    @Test
    public void 全ての項目が未指定のとき全てのデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"),
                new Book(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findAll();
        List<Book> actual = bookService.findBy("", "", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 指定した文字列を含むカテゴリーに該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"));

        doReturn(allBooks).when(bookMapper).findBy("ラ", "", null);
        List<Book> actual = bookService.findBy("ラ", "", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 指定した文字列を含む書籍名に該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "の", null);
        List<Book> actual = bookService.findBy("", "の", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 購入済みの全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"),
                new Book(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "", true);
        List<Book> actual = bookService.findBy("", "", true);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 未購入の全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"));

        doReturn(allBooks).when(bookMapper).findBy("", "", false);
        List<Book> actual = bookService.findBy("", "", false);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 指定した文字を含むデータが存在しないとき空のデータを返す() {
        List<Book> allBooks = List.of(new Book[0]);

        doReturn(allBooks).when(bookMapper).findBy("no", " ", null);
        List<Book> actual = bookService.findBy("no", " ", null);
        assertThat(actual).isEqualTo(allBooks);
    }

    @Test
    public void 存在する本のIDを指定したときに正常に本のデータを返す() throws BookNotFoundException {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"))).when(bookMapper).findByBookId(1);

        Book actual = bookService.findBook(1);
        assertThat(actual).isEqualTo(new Book(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"));
    }
}
