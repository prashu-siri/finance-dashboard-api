package com.self.financedashboard.repository;

import com.self.financedashboard.model.Summary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, Integer> {
}
