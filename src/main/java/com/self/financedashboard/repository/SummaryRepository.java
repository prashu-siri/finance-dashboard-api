package com.self.financedashboard.repository;

import com.self.financedashboard.model.Summary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, Integer> {

    Optional<Summary> findBySymbol(String stockSymbol);
    void deleteBySymbol(String stockSymbol);

    @Query(value = "DELETE FROM SUMMARY s where s.user_id = :id", nativeQuery = true)
    void deleteAllStocks(int id);
}
