package com.ookawara.book.application.service;

import com.ookawara.book.application.entity.BookAllData;
import com.ookawara.book.application.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    BookService bookService;

    @Mock
    BookMapper bookMapper;

    @Test
    public void 全ての項目が未指定のとき全てのデータを取得() {
        List<BookAllData> bookAllData = List.of(
                new BookAllData(1, "ノーゲーム・ノーライフ・1", "2012/04/30", true, 2, "ライトノベル"),
                new BookAllData(2, "鬼滅の刃・1", "2016/06/08", false, 1, "漫画"),
                new BookAllData(3, "ビブリア古書堂の事件手帖・1", "2011/03/25", true, 3, "小説"));

        doReturn(bookAllData).when(bookMapper).findAll();
        List<BookAllData> actual = bookService.findBy("", "", null);
        assertThat(actual).isEqualTo(bookAllData);
    }
}
