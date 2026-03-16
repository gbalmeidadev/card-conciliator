package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Installment;
import com.conciliator.card_conciliator.entity.InstallmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface InstallmentRepository extends JpaRepository<Installment, Long> {

    List<Installment> findBySaleId(Long saleId);

    List<Installment> findBySaleIdAndInstallmentNumber(Long saleId, Integer installmentNumber);

    List<Installment> findByStatus(InstallmentStatus status);

    List<Installment> findByPaymentDate(LocalDate paymentDate);

    List<Installment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    List<Installment> findBySaleIdAndStatus(Long saleId, InstallmentStatus status);

    /*
    Buscar parcelas pendentes de pagamento
     */
    @Query("""
        SELECT i
        FROM Installment i
        WHERE i.status <> 'PAID'
    """)
    List<Installment> findPendingInstallment();

    /*
    Buscar parcelas pagas
     */
    @Query("""
        SELECT i
        FROM Installment i
        WHERE i.status = 'PAID'
    """)
    List<Installment> findPaidInstallment();

    /*
    Buscar parcelas atrasadas
     */
    @Query("""
        SELECT i
        FROM Installment i
        WHERE i.paymentDate < CURRENT_DATE
        AND i.status <> 'PAID'
    """)
    List<Installment> findOverdueInstallment();

    /*
    Buscar parcela de uma venda ordenada
     */
    @Query("""
        SELECT i
        FROM Installment i
        WHERE i.sale.id = :saleId
        ORDER BY i.installmentNumber
    """)
    List<Installment> findInstallmentBySaleOrdered(Long saleId);
}
