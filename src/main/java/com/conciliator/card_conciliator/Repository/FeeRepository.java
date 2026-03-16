package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Fee;
import com.conciliator.card_conciliator.entity.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    List<Fee> findByEnterpriseId(Long enterpriseId);

    List<Fee> findByType(FeeType type);

    List<Fee> findByEnterpriseIdAndType(Long enterpriseId, FeeType type);

    List<Fee> findByCardBrand(String cardBrand);

    List<Fee> findByTransactionType(String transactionType);

    List<Fee> findByEnterpriseIdAndActiveTrue(Long enterpriseId);

    Optional<Fee> findByEnterpriseIdAndCardBrandAndTransactionType(
            Long enterpriseId,
            String cardBrand,
            String transactionType
    );

    List<Fee> findByEnterpriseIdAndMaxInstallmentsLessThanEqual(
            Long enterpriseId,
            Integer maxInstallments
    );

    /*
    Buscar taxas ativas
     */
    @Query("""
        SELECT f
        FROM Fee f
        WHERE f.active = true
    """)
    List<Fee> findActiveFees();

    /*
    Buscar taxas inativas
     */
    @Query("""
        SELECT f
        FROM Fee f
        WHERE f.active = false
    """)
    List<Fee> findInactiveFee();

    /*
    Buscar taxa especifica para calculo financeiro
     */
    @Query("""
        SELECT f
        FROM Fee f
        WHERE f.enterprise.id = :enterpriseId
        AND f.cardBrand = :cardBrand
        AND f.transactionType = :transactionType
        AND f.active = true
    """)
    List<Fee> findActiveFeeRules(Long enterpriseId, String cardBrand, String transactionType);
}
