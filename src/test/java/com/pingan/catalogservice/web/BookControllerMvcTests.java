package com.pingan.catalogservice.web;

import com.pingan.catalogservice.domain.BookNotFoundException;
import com.pingan.catalogservice.domain.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;//在 mock 环境中测试 web 层的工具类
    @MockBean
    private BookService bookService; //添加 mock 的 BookService 到 Spring 应用上下文中

    @Test
    void whenGetBookNotExistingThenShouldReturn404() throws Exception {
        String isbn = "1234567891";
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException.class);//定义 BookService mock bean 的预期行为
        mockMvc.perform(get("/books" + isbn))
                .andExpect(status().isNotFound()); //预期响应的状态码为“404 Not Found”
    }

}
