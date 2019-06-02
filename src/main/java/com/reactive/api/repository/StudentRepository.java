package com.reactive.api.repository;

import com.reactive.api.model.StudentDetails;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveMongoRepository<StudentDetails, String> {
}
