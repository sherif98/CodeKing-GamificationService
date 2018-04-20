package com.codeking.gamificationservice.repository;

import com.codeking.gamificationservice.domain.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends MongoRepository<Problem, String> {
}
