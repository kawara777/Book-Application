package integrationtest;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import com.ookawara.book.application.BookApplication;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@SpringBootTest(classes = BookApplication.class)
@AutoConfigureMockMvc
@DBRider
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRestApiIntegrationTest {

    @Autowired
    MockMvc mockMvc;

//    GET

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 本のデータが全件取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 1,
                               "name": "ノーゲーム・ノーライフ・1",
                               "releaseDate": 2012-04-30,
                               "isPurchased": true,
                               "categoryId": 2,
                               "category": "ライトノベル"
                           },
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           },
                           {
                               "bookId": 3,
                               "name": "ビブリア古書堂の事件手帖・1",
                               "releaseDate": 2011-03-25,
                               "isPurchased": true,
                               "categoryId": 3,
                               "category": "小説"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列を含む書籍名に該当する全ての本のデータを取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?name=の"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           },
                           {
                               "bookId": 3,
                               "name": "ビブリア古書堂の事件手帖・1",
                               "releaseDate": 2011-03-25,
                               "isPurchased": true,
                               "categoryId": 3,
                               "category": "小説"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列の年月日を含む発売日に該当する全ての本のデータを取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?releaseDate=2016-06-08"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列の年月日が存在しない日付のときに400が返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/books?releaseDate=2021-02-29"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "存在する日付を入力してください。",
                    "path": "/books"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列の年月を含む発売日に該当する全ての本のデータを取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?releaseDate=2016-06"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列の年を含む発売日に該当する全ての本のデータを取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?releaseDate=2016"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列の発売日が指定されたフォーマットではないときに400が返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/books?releaseDate=2021/02/29"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "YYYY-MM-DD の形式（例：2000-01-01）で年か、年月か、年月日を指定してください。",
                    "path": "/books"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 購入済みの本のデータを全て取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?isPurchased=yes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 1,
                               "name": "ノーゲーム・ノーライフ・1",
                               "releaseDate": 2012-04-30,
                               "isPurchased": true,
                               "categoryId": 2,
                               "category": "ライトノベル"
                           },
                           {
                               "bookId": 3,
                               "name": "ビブリア古書堂の事件手帖・1",
                               "releaseDate": 2011-03-25,
                               "isPurchased": true,
                               "categoryId": 3,
                               "category": "小説"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 未購入の本のデータを全て取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?isPurchased=no"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 2,
                               "name": "鬼滅の刃・1",
                               "releaseDate": 2016-06-08,
                               "isPurchased": false,
                               "categoryId": 1,
                               "category": "漫画"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列を含むカテゴリーに該当する全ての本のデータを取得できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?category=ノ"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        [
                           {
                               "bookId": 1,
                               "name": "ノーゲーム・ノーライフ・1",
                               "releaseDate": 2012-04-30,
                               "isPurchased": true,
                               "categoryId": 2,
                               "category": "ライトノベル"
                           }
                        ]
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void クエリパラメーターで指定した文字列を含むデータが存在しないときに空のデータを返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books?category= &name=ki&isPurchased=yes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        []
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在する本のIDを指定したときに正常に本のデータを返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "bookId": 1,
                            "name": "ノーゲーム・ノーライフ・1",
                            "releaseDate": 2012-04-30,
                            "isPurchased": true,
                            "categoryId": 2,
                            "category": "ライトノベル"
                        }
                        """
                ));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在しない本のIDを指定したときにステータスコードが404となり例外メッセージが返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/book/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "book：0 のデータはありません。",
                    "path": "/book/0"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在するカテゴリーのIDを指定したときに正常にカテゴリーのデータを返すこと() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/category/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "categoryId": 1,
                            "category": "漫画"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 存在しないカテゴリーのIDを指定したときにステータスコードが404となり例外メッセージが返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.get("/category/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "category：0 のデータはありません。",
                    "path": "/category/0"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

//    POST

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/create/create-books.yml", ignoreCols = "book_id")
    @Transactional
    void 書籍データが正常に登録できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "鬼滅の刃・2",
                                    "releaseDate": "2016-08-09",
                                    "isPurchased": false,
                                    "categoryId": 1
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "正常に登録されました。"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void すでに存在する書籍データを登録したときにステータスコードが409となり設定したエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "ノーゲーム・ノーライフ・1",
                                    "releaseDate": "2012-04-30",
                                    "isPurchased": true,
                                    "categoryId": 2
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "409",
                    "error": "Conflict",
                    "message": "すでに登録されています。",
                    "path": "/books"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 登録する書籍データに存在しないカテゴリーIDを指定したときに例外のエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "ノーゲーム・ノーライフ・1",
                                    "releaseDate": "2012-04-30",
                                    "isPurchased": true,
                                    "categoryId": 100
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "categoryId：100 のデータがありません。",
                    "path": "/books"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/create/create-categories.yml", ignoreCols = "category_id")
    @Transactional
    void カテゴリーが正常に登録できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "エッセイ"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "正常に登録されました。"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void すでに存在するカテゴリーを登録したときにステータスコードが409となり設定したエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "category": "漫画"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "409",
                    "error": "Conflict",
                    "message": "すでに登録されています。",
                    "path": "/categories"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

//    PATCH

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-allColumn.yml")
    @Transactional
    void 書籍データが正常に更新できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "鬼滅の刃 1",
                                    "releaseDate": "2016/07/08",
                                    "isPurchased": true,
                                    "categoryId": 2
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "正常に更新されました。"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet(value = "datasets/update/update-books-name.yml")
    @Transactional
    void 書籍名のみ正常に更新できること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "鬼滅の刃 1"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "正常に更新されました。"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 指定した本のIDが存在しないときに例外のエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/books/0")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "鬼滅の刃 1",
                                    "releaseDate": "2016/07/08",
                                    "isPurchased": true,
                                    "categoryId": 1
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "book：0 のデータはありません。",
                    "path": "/books/0"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 更新する書籍データに存在しないカテゴリーIDを指定したときに例外のエラーメッセージを返すこと() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "鬼滅の刃 1",
                                    "releaseDate": "2016/07/08",
                                    "isPurchased": true,
                                    "categoryId": 999999999
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "categoryId：999999999 のデータがありません。",
                    "path": "/books/2"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 更新する発売日にフォーマットが違う文字列を指定したとき例外が発生すること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.patch("/books/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "releaseDate": "2024-02-29"
                                }
                                """))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "400",
                    "error": "Bad Request",
                    "message": "YYYY/MM/DD の形式（例：2000/01/01）で存在する日付を入力してください。",
                    "path": "/books/2"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }

    @Test
    @DataSet("datasets/books.yml")
    @ExpectedDataSet("datasets/delete/delete-books.yml")
    @Transactional
    void 存在する本のIDを指定したときに正常に本のデータが削除されること() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("""
                        {
                            "message": "正常に削除されました。"
                        }
                        """));
    }

    @Test
    @DataSet("datasets/books.yml")
    @Transactional
    void 削除時に存在しない本のIDを指定したときステータスコードが404となり例外メッセージが返されること() throws Exception {
        String response = mockMvc.perform(MockMvcRequestBuilders.delete("/book/0"))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals("""
                {
                    "timestamp": "2024-01-01 00:00:00.000000+09:00[Asia/Tokyo]",
                    "status": "404",
                    "error": "Not Found",
                    "message": "book：0 のデータはありません。",
                    "path": "/book/0"
                }
                """, response, new CustomComparator(JSONCompareMode.STRICT, new Customization("timestamp", (o1, o2) -> true
        )));
    }
}
