package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Conciliation;
import com.conciliator.card_conciliator.entity.DivergenceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConciliationRepository extends JpaRepository<Conciliation, Long> {

    List<Conciliation> findByEnterpriseId(Long enterpriseId);

    List<Conciliation> findBySaleId(Long saleId);

    List<Conciliation> findByInstallmentId(Long installmentId);

    List<Conciliation> findByDivergencyType(DivergenceType divergenceType);

    /*
    Buscar conciliações corretas(sem diferença)
     */
    @Query("""
    SELECT c
    FROM Conciliation c
    WHERE c.differenceAmount = 0
    """)
    List<Conciliation> findConciliationWithDifference();

    /*
    Buscar conciliações com divergencia por empresa
     */
    @Query("""
    SELECT c
    FROM Conciliation c
    WHERE c.enterprise.id = :enterpriseId
    AND c.differenceAmount <> 0
    """)
    List<Conciliation> findDivergencesByEnterprise(Long enterpriseId);

    /*
    Buscar conciliações de uma empresa por tipo de divergencia
     */
    @Query("""
    SELECT c
    FROM Conciliation c
    WHERE c.enterprise.id = :enterpriseId
    AND c.divergencyType = :divergencyType
    """)
    List<Conciliation> findEnterpriseAndDivergenceType(Long enterpriseId, DivergenceType divergenceType);

    /*
    Buscar conciliações de uma venda especifica
     */
    @Query("""
    SELECT c
    FROM Conciliation c
    WHERE c.sale.id = :saleId
    """)
    List<Conciliation> findConciliationBySale(Long saleId);

    /*
    Buscar conciliação de uma transação especifica
     */
    @Query("""
    SELECT c
    FROM Conciliation c
    WHERE c.operationTransaction.id = :transactionId
    """)
    List<Conciliation> findConciliationByOperationTransaction(Long transactionId);
}