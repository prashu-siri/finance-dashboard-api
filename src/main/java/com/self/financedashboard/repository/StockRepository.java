package com.self.financedashboard.repository;

import com.self.financedashboard.model.Stock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {
    List<Stock> findByStockSymbolAndUserId(String stockSymbol, int userid);

    @Query(value = "DELETE FROM STOCK s where s.id = :id", nativeQuery = true)
    void deleteAllStocks(int id);

    @Query(value = "SELECT * FROM STOCK s WHERE s.user_id= :userId", nativeQuery = true)
    List<Stock> findUserStocks(int userId);
}
