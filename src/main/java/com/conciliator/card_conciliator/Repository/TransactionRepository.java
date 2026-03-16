package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.OperationTransaction;
import com.conciliator.card_conciliator.entity.Sale;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<OperationTransaction, Long> {
    Optional<OperationTransaction> findByNsu(String nsu);

    Optional<OperationTransaction> findByAuthorizationCode(String authorizationCode);

    boolean existsByNsu(String nsu);

    boolean existsByTransactionCode(String transactionCode);

    List<OperationTransaction> findByTransactionDate(LocalDate transactionDate);

    List<OperationTransaction> findByOperatorName(String operatorName);

    List<OperationTransaction> findByEnterpriseId(Long enterpriseId);

    List<OperationTransaction> findByEnterpriseIdAndTransactionDateBetween(
            Long enterpriseId,
            LocalDate TransactionDate,
            LocalDate endDate
    );

    List<OperationTransaction> findByEnterpriseIdAndCardBrand(
            Long enterpriseId,
            String cardBrand
    );

    List<OperationTransaction> findByEnterpriseIdAndTransactionType(
            Long enterpriseId,
            String transactionType
    );

    /*
    Venda e transação com valores
     */
    @Query("""
    SELECT s
    FROM Sale s
    JOIN OperationTransaction t ON t.nsu = s.nsu
    WHERE s.grossAmount <> t.grossAmount
    """)
    List<Sale> findSaleWithDifferentAmount();

    /*
    Venda registrada mas sem transação na operadora
     */
    @Query("""
    SELECT t
    FROM Sale s
    LEFT JOIN OperationTransaction t ON t.nsu = s.nsu
    WHERE t.id IS NULL
    """)
    List<Sale> findSaleWithoutTransaction();

    /*
    Transação da operadora sem venda no sistema
     */
    @Query("""
    SELECT t
    FROM OperationTransaction t
    LEFT JOIN Sale s ON s.nsu = t.nsu
    WHERE s.id IS Null
    """)
    List<Sale> findTransactionWithoutSale();

    /*
    Tranação duplicada
     */
    @Query("""
    SELECT t.nsu, COUNT(t)
    FROM OperationTransaction t 
    GROUP BY t.nsu
    HAVING COUNT(t) > 1
    """)
    List<OperationTransaction> findDuplicatedTransaction();
}
