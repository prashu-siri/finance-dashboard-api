package com.self.financedashboard.repository;

import com.self.financedashboard.model.Summary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SummaryRepository extends CrudRepository<Summary, Integer> {

    @Query(value = "DELETE FROM SUMMARY s where s.user_id = :id", nativeQuery = true)
    void deleteAllStocks(int id);

    void deleteBySymbolAndUserId(String stockSymbol, int userId);

    Optional<Summary> findBySymbolAndUserId(String stockSymbol, int userId);

    @Query(value = "SELECT * FROM SUMMARY s WHERE s.user_id = :userId", nativeQuery = true)
    List<Summary> findAllUserStocks(int userId);
}
