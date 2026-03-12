package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Chargeback;
import com.conciliator.card_conciliator.entity.ChargebackStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChargebackRepository extends JpaRepository<Chargeback, Long> {

    List<Chargeback> findByEnterpriseId(Long enterpriseId);

    List<Chargeback> findBySaleId(Long saleId);

    List<Chargeback> findByInstallmentId(Long installmentId);

    List<Chargeback> findByOperationTransactionId(Long operationTransactionId);

    List<Chargeback> findByStatus(ChargebackStatus status);

    List<Chargeback> findByEnterpriseIdAndStatus(Long enterpriseId, ChargebackStatus status);

    List<Chargeback> findByChargebackDate(LocalDate chargebackDate);

    List<Chargeback> findByEnterpriseIdAndChargebackDate(
            Long enterpriseId,
            LocalDate startDate,
            LocalDate endDate);

    List<Chargeback> findByReasonCode(String reasonCode);
}
