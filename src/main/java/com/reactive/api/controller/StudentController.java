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

@RestController
@RequestMapping("api/student")
public class StudentController {

    private final StudentRepository detailsRepository;

    @Autowired
    public StudentController(StudentRepository detailsRepository) {
        this.detailsRepository = detailsRepository;
    }

    @GetMapping(name = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StudentDetails> getUserDetails() {
        return detailsRepository.findAll();
    }


    @PostMapping
    public Mono<StudentDetails> saveStudentDetails(@RequestBody StudentDetails studentDetails) {
        return detailsRepository.save(studentDetails);
    }

    @PutMapping("/{id}")
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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<StudentDetails>> getStudentDetails(@PathVariable String id) {
        return detailsRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteStudentDetails(@PathVariable String id) {
        return detailsRepository.findById(id)
                .flatMap(studentDetails -> detailsRepository.delete(studentDetails)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<StudentDetails> getAllStudents() {
        return detailsRepository.findAll();
    }
}

