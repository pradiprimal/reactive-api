package com.reactive.api.controller;

import com.reactive.api.model.StudentDetails;
import com.reactive.api.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("api/")
public class StudentController {

    private static final String STUDENT_BASE_API = "student";

    private final StudentRepository detailsRepository;

    @Autowired
    public StudentController(StudentRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping(value = "stream/student", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StudentDetails> getUserDetails() {
        return detailsRepository.findAll().delayElements(Duration.ofSeconds(2));
    }


    @PostMapping(STUDENT_BASE_API)
    public Mono<StudentDetails> saveStudentDetails(@RequestBody StudentDetails studentDetails) {
        return detailsRepository.save(studentDetails);
    }

    @PutMapping(STUDENT_BASE_API + "/{id}")
    public Mono<ResponseEntity<StudentDetails>> updateStudentDetails(@PathVariable String id,
                                                                     @RequestBody StudentDetails studentDetails) {
        return detailsRepository.findById(id)
                .flatMap(student -> {
                    student.setPhone(studentDetails.getPhone());
                    student.setAddress(studentDetails.getAddress());
                    return detailsRepository.save(student);
                }).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(STUDENT_BASE_API + "/{id}")
    public Mono<ResponseEntity<StudentDetails>> getStudentDetails(@PathVariable String id) {
        return detailsRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(STUDENT_BASE_API + "/{id}")
    public Mono<ResponseEntity<Void>> deleteStudentDetails(@PathVariable String id) {
        return detailsRepository.findById(id)
                .flatMap(studentDetails -> detailsRepository.delete(studentDetails)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(STUDENT_BASE_API)
    public Flux<StudentDetails> getAllStudents() {
        return detailsRepository.findAll();
    }
}

