package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Sale;
import com.conciliator.card_conciliator.entity.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByEnterpriseId(Long enterpriseId);

    List<Sale> findByEnterpriseIdAndSaleDateBetween(
            Long enterpriseId,
            LocalDate startDate,
            LocalDate endDate
    );

    Optional<Sale> findBySaleCode(String saleCode);

    Optional<Sale> findByNsu(String nsu);

    boolean existsBySaleCode(String saleCode);

    List<Sale> findByEnterpriseIdAndStatus(long enterpriseId, SaleStatus status);
}
