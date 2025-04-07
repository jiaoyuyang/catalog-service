package com.pingan.catalogservice;

import com.pingan.catalogservice.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        //加载完整的 spring web 应用上下文以及监听任意端口的 servlet 容器
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")  //启用 "integration" profile 以加载 application-integration.yml 中的配置
class CatalogServiceApplicationTests {
    @Autowired
    private WebTestClient webTestClient; //为了测试而执行rest 调用的工具

    @Test
    void whenPostRequestThenBookCreated(){
        var expectedBook = Book.of("1234567891", "Title", "Author", 9.90);
        webTestClient
                .post()//发送 HTTP POST 请求
                .uri("/books")//发送请求到/books 端点
                .bodyValue(expectedBook)//向请求体中添加图书
                .exchange()//发送请求
                .expectStatus().isCreated()//检验 HTTP 响应的状态吗为“201 Created”
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();//校验 HTTP 响应中包含一个非空的响应体
                    assertThat(actualBook.isbn()).isEqualTo(expectedBook.isbn()); //校验创建的对象符合预期
                });
    }



    @Test
    void contextLoads() {
    }

}
