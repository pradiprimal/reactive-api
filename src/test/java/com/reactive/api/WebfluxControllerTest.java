package com.reactive.api;

import com.reactive.api.model.StudentDetails;
import com.reactive.api.repository.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebfluxControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StudentRepository detailsRepository;


    @Test
    public void testShouldGetAllStudents() {
        webTestClient.get()
                .uri("api/student")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StudentDetails.class);
    }
}
