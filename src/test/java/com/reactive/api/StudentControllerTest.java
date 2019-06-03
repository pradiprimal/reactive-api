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
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    private static final String STUDENT_BASE_API = "api/student";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private StudentRepository detailsRepository;


    @Test
    public void testShouldGetAllStudents() {
        webTestClient.get()
                .uri(STUDENT_BASE_API)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .returnResult(StudentDetails.class);

    }

    @Test
    public void testShouldSaveStudent() {
        StudentDetails studentDetails = new StudentDetails("test", "test@gmail.com", "1234567");
        webTestClient.post()
                .uri(STUDENT_BASE_API)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(studentDetails), StudentDetails.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.name").isEqualTo("test")
                .jsonPath("$.email").isEqualTo("test@gmail.com")
                .jsonPath("$.phone").isNotEmpty();
    }

    public void testShouldUpdateStudent() {

    }
}
