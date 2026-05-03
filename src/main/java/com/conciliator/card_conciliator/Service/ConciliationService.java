package com.conciliator.card_conciliator.Service;

import com.conciliator.card_conciliator.Repository.*;
import com.conciliator.card_conciliator.dto.ConciliationResponseDTO;
import com.conciliator.card_conciliator.dto.ConciliationSummaryDTO;
import com.conciliator.card_conciliator.entity.*;
import com.conciliator.card_conciliator.mapper.ConciliationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConciliationService {

    private final SaleRepository saleRepository;
    private final TransactionRepository transactionRepository;
    private final FeeRepository feeRepository;
    private final InstallmentRepository installmentRepository;
    private final ConciliationRepository conciliationRepository;

    public List<ConciliationResponseDTO> conciliationByNsu(String nsu) {

        log.info("Iniciando conciliação NSU={}", nsu);

        Sale sale = saleRepository.findByNsu(nsu)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        OperationTransaction transaction = transactionRepository.findByNsu(nsu)
                .orElseThrow (() -> new RuntimeException("Transação encontrada"));

        List<Installment> installments = installmentRepository.findBySaleId(sale.getId());

        List<ConciliationResponseDTO> result = new ArrayList<>();

        for (Installment installment : installments) {
            BigDecimal expected = calculateExpectedAmount(sale, installment);
            BigDecimal received = transaction.getNetAmount();

            DivergenceType divergence = identifyDivergence(sale, transaction, installment, expected, received);

            Conciliation conciliation = buildConciliation(
                    sale,
                    transaction,
                    installment,
                    expected,
                    received,
                    divergence
            );

            conciliationRepository.save(conciliation);
            result.add(ConciliationMapper.toResponse(conciliation));
        }

        log.info("Conciliação finalizada NSU{}", nsu);
        return result;
    }

    public List<ConciliationResponseDTO> conciliationBatch(Long enterpriseId) {
        List<Sale> sales = saleRepository.findByEnterpriseId(enterpriseId);

        List<ConciliationResponseDTO> result = new ArrayList<>();

        for (Sale sale : sales) {
            try {
                result.addAll(conciliationByNsu(sale.getNsu()));
            } catch (Exception e) {
                log.error("Erro ao conciliar venda {}", sale.getId(), e);
            }
        }
        return result;
    }

    public List<ConciliationResponseDTO> findAll() {
        return conciliationRepository.findAll()
                .stream()
                .map(ConciliationMapper::toResponse)
                .toList();
    }

    public List<ConciliationSummaryDTO> findAllSummary() {
        return conciliationRepository.findAll()
                .stream()
                .map(ConciliationMapper::toSummary)
                .toList();
    }

    public List<ConciliationResponseDTO> findDivergences() {
        return conciliationRepository.findAll()
                .stream()
                .filter(c -> c.getDivergencyType() != null)
                .map(ConciliationMapper::toResponse)
                .toList();
    }

    public List<ConciliationResponseDTO> findByEnterprise(Long enterpriseId) {
        return conciliationRepository.findAll()
                .stream()
                .filter(c -> c.getEnterprise().getId().equals(enterpriseId))
                .map(ConciliationMapper::toResponse)
                .toList();
    }

    private BigDecimal calculateExpectedAmount(Sale sale, Installment installment) {

        BigDecimal gross = installment.getInstallmentAmount();

        Fee fee = feeRepository
                .findByEnterpriseIdAndCardBrandAndTransactionType(
                        sale.getEnterprise().getId(),
                        sale.getCardBrand(),
                        sale.getTransactionType()
                )
                .orElse(null);

        if (fee == null) {
            log.warn("Taxa não encontrada para venda {}", sale.getId());
            return gross;
        }

        BigDecimal feeValue = calculateFee(gross, fee);

        return gross.subtract(feeValue).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateFee(BigDecimal amount, Fee fee) {

        BigDecimal percentageValue =BigDecimal.ZERO;

        if (fee.getPercentage() != null) {
            percentageValue = amount.multiply(fee.getPercentage())
                    .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        }

        BigDecimal fixed = fee.getFixedValue() != null
                ? fee.getFixedValue()
                : BigDecimal.ZERO;

        return percentageValue.add(fixed);
    }

    private DivergenceType identifyDivergence(
            Sale sale,
            OperationTransaction transaction,
            Installment installment,
            BigDecimal expected,
            BigDecimal received
    ) {
        if (received == null) return  DivergenceType.UNKNOWN;

        if (expected.compareTo(received) != 0) {
            return DivergenceType.AMOUNT_DIFERENCE;
        }

        if (!sale.getInstallment().equals(transaction.getInstallment())) {
            return DivergenceType.INSTALLMENT_DIFERENCE;
        }

        if (!sale.getSaleDate().equals(transaction.getInstallment())) {
            return DivergenceType.DATE_DIFERENCE;
        }

        return null;
    }

    private Conciliation buildConciliation(
            Sale sale,
            OperationTransaction transaction,
            Installment installment,
            BigDecimal expected,
            BigDecimal received,
            DivergenceType divergence
    ) {
        return Conciliation.builder()
                .enterprise(sale.getEnterprise())
                .sale(sale)
                .operationTransaction(transaction)
                .installment(installment)
                .expectedAmount(expected)
                .receivedAmount(received)
                .differenceAmount(expected.subtract(received))
                .divergencyType(divergence)
                .notes(divergence != null ? "Divergência encontrada" : "OK")
                .build();
    }
}
