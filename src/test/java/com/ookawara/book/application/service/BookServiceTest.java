package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.Book;
import com.ookawara.book.application.entity.Category;
import com.ookawara.book.application.exception.ResourceDuplicateException;
import com.ookawara.book.application.exception.ResourceNotFoundException;
import com.ookawara.book.application.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookMapper bookMapper;

//    GET

    @Test
    public void 全ての項目が未指定のとき全てのデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findAll();
        List<Book> actual = bookService.findBy("", "", null, "");
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findAll();
    }

    @Test
    public void 指定した文字列を含む書籍名に該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("の", "", null, "");
        List<Book> actual = bookService.findBy("の", "", null, "");
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("の", "", null, "");
    }

    @Test
    public void 指定した文字列を含む発売日に該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"));

        doReturn(allBooks).when(bookMapper).findBy("", "2016-06", null, "");
        List<Book> actual = bookService.findBy("", "2016-06", null, "");
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "2016-06", null, "");
    }

    @Test
    public void 購入済みの全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"),
                new Book(3, "ビブリア古書堂の事件手帖・1", LocalDate.of(2011, 3, 25), true, 3, "小説"));

        doReturn(allBooks).when(bookMapper).findBy("", "", true, null);
        List<Book> actual = bookService.findBy("", "", true, null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "", true, null);
    }

    @Test
    public void 未購入の全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(2, "鬼滅の刃・1", LocalDate.of(2016, 6, 8), false, 1, "漫画"));

        doReturn(allBooks).when(bookMapper).findBy("", "", false, null);
        List<Book> actual = bookService.findBy("", "", false, null);
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("", "", false, null);
    }

    @Test
    public void 指定した文字列を含むカテゴリーに該当する全ての本のデータを取得() {
        List<Book> allBooks = List.of(
                new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"));

        doReturn(allBooks).when(bookMapper).findBy(" ", "", null, "ラ");
        List<Book> actual = bookService.findBy(" ", "", null, "ラ");
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy(" ", "", null, "ラ");
    }

    @Test
    public void 指定した文字列を含むデータが存在しないとき空のデータを返す() {
        List<Book> allBooks = List.of(new Book[0]);

        doReturn(allBooks).when(bookMapper).findBy("no", " ", null, "い");
        List<Book> actual = bookService.findBy("no", " ", null, "い");
        assertThat(actual).isEqualTo(allBooks);
        verify(bookMapper).findBy("no", " ", null, "い");
    }

    @Test
    public void 存在する本のIDを指定したときに正常に本のデータを返す() {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル")))
                .when(bookMapper).findByBookId(1);
        Book actual = bookService.findBook(1);
        assertThat(actual).isEqualTo(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2, "ライトノベル"));
        verify(bookMapper).findByBookId(1);
    }

    @Test
    public void 存在しない本のIDを指定したときに例外のエラーメッセージを返す() {
        doReturn(Optional.empty()).when(bookMapper).findByBookId(0);
        assertThatThrownBy(() -> bookService.findBook(0))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("book：" + 0 + " のデータはありません。");
        verify(bookMapper).findByBookId(0);
    }

    @Test
    public void 存在するカテゴリーのIDを指定したときに正常にカテゴリーのデータを返す() {
        doReturn(Optional.of(new Category(1, "漫画"))).when(bookMapper).findByCategoryId(1);
        Category actual = bookService.findCategory(1);
        assertThat(actual).isEqualTo(new Category(1, "漫画"));
        verify(bookMapper).findByCategoryId(1);
    }

    @Test
    public void 存在しないカテゴリーのIDを指定したときに例外のエラーメッセージを返す() {
        doReturn(Optional.empty()).when(bookMapper).findByCategoryId(0);
        assertThatThrownBy(() -> bookService.findCategory(0))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("category：" + 0 + " のデータはありません。");
        verify(bookMapper).findByCategoryId(0);
    }

//    POST

    @Test
    public void 本のデータを正常に登録できること() {
        doReturn(Optional.of(new Category(1, "漫画"))).when(bookMapper).findByCategoryId(1);
        Book book = new Book("鬼滅の刃・2", LocalDate.of(2016, 8, 9), false, 1);
        doNothing().when(bookMapper).insertBook(book);
        Book actual = bookService.createBook("鬼滅の刃・2", LocalDate.of(2016, 8, 9), false, 1);
        assertThat(actual).isEqualTo(book);
        verify(bookMapper).findByCategoryId(1);
        verify(bookMapper).insertBook(book);
    }

    @Test
    public void すでに存在する書籍データを登録しようとしたときに例外のエラーメッセージを返すこと() {
        doReturn(Optional.of(new Category(2, "ライトノベル"))).when(bookMapper).findByCategoryId(2);
        doReturn(Optional.of(new Book("ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2)))
                .when(bookMapper).findBookBy("ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2);
        assertThatThrownBy(() -> bookService.createBook("ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2))
                .isInstanceOf(ResourceDuplicateException.class)
                .hasMessage("すでに登録されています。");
        verify(bookMapper).findByCategoryId(2);
        verify(bookMapper).findBookBy("ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2);
    }

    @Test
    public void 登録する書籍データに存在しないカテゴリーIDを指定したときに例外のエラーメッセージを返すこと() {
        doReturn(Optional.empty()).when(bookMapper).findByCategoryId(999999999);
        assertThatThrownBy(() -> bookService.createBook("ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 999999999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("categoryId：" + 999999999 + " のデータがありません。");
        verify(bookMapper).findByCategoryId(999999999);
    }

    @Test
    public void カテゴリーを正常に登録できること() {
        Category category = new Category("エッセイ");
        doNothing().when(bookMapper).insertCategory(category);
        Category actual = bookService.createCategory("エッセイ");
        assertThat(actual).isEqualTo(category);
        verify(bookMapper).insertCategory(category);
    }

    @Test
    public void すでに存在するカテゴリーを登録しようとしたときに例外のエラーメッセージを返すこと() {
        doReturn(Optional.of(new Category("漫画")))
                .when(bookMapper).findCategory("漫画");
        assertThatThrownBy(() -> bookService.createCategory("漫画"))
                .isInstanceOf(ResourceDuplicateException.class)
                .hasMessage("すでに登録されています。");
        verify(bookMapper).findCategory("漫画");
    }

//    PATCH

    @Test
    public void 本のデータを正常に更新できること() {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2)))
                .when(bookMapper).findByBookId(1);
        doReturn(Optional.of(new Category(3, "小説"))).when(bookMapper).findByCategoryId(3);
        Book book = new Book(1, "ノーゲーム・ノーライフ 1", LocalDate.of(2012, 5, 30), false, 3);
        doNothing().when(bookMapper).updateBook(book);
        Book actual = bookService.updateBook(1, "ノーゲーム・ノーライフ 1", LocalDate.of(2012, 5, 30), false, 3);
        assertThat(actual).isEqualTo(book);
        verify(bookMapper).findByBookId(1);
        verify(bookMapper).findByCategoryId(3);
        verify(bookMapper).updateBook(book);
    }

    @Test
    void 指定した本のIDに対して更新するレコードが既にあるとき上書きとして正常に更新できること() {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2)))
                .when(bookMapper).findByBookId(1);
        doReturn(Optional.of(new Category(2, "ライトノベル"))).when(bookMapper).findByCategoryId(2);
        Book book = new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2);
        doNothing().when(bookMapper).updateBook(book);
        Book actual = bookService.updateBook(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2);
        assertThat(actual).isEqualTo(book);
        verify(bookMapper).findByBookId(1);
        verify(bookMapper).findByCategoryId(2);
        verify(bookMapper).updateBook(book);
    }

    @Test
    void 更新時に指定した本のIDがない時例外のエラーメッセージを返すこと() {
        doReturn(Optional.empty()).when(bookMapper).findByBookId(999999999);
        assertThatThrownBy(
                () -> bookService.updateBook(999999999, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("book：999999999 のデータはありません。");
        verify(bookMapper).findByBookId(999999999);
    }

    @Test
    void 更新時に指定したカテゴリーIDに対するデータがないとき例外のエラーメッセージを返すこと() {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2)))
                .when(bookMapper).findByBookId(1);
        doReturn(Optional.empty()).when(bookMapper).findByCategoryId(999999999);
        assertThatThrownBy(
                () -> bookService.updateBook(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 999999999))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("categoryId：999999999 のデータがありません。");
        verify(bookMapper).findByBookId(1);
        verify(bookMapper).findByCategoryId(999999999);
    }

    //    DELETE
    @Test
    void 指定した本のIDのデータを正常に削除できること() {
        doReturn(Optional.of(new Book(1, "ノーゲーム・ノーライフ・1", LocalDate.of(2012, 4, 30), true, 2)))
                .when(bookMapper).findByBookId(1);
        doNothing().when(bookMapper).deleteBook(1);
        bookService.deleteBook(1);
        verify(bookMapper).findByBookId(1);
        verify(bookMapper).deleteBook(1);
    }

    @Test
    void 指定した本のIDが存在しない時データが削除されないこと() {
        doReturn(Optional.empty()).when(bookMapper).findByBookId(100);
        assertThatThrownBy(() -> bookService.deleteBook(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("book：100 のデータはありません。");
        verify(bookMapper).findByBookId(100);
    }
}
