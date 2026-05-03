package com.conciliator.card_conciliator.controller;


import com.conciliator.card_conciliator.Service.ConciliationService;
import com.conciliator.card_conciliator.dto.ConciliationResponseDTO;
import com.conciliator.card_conciliator.dto.ConciliationSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conciliations")
@RequiredArgsConstructor
public class ConciliationController {

    private final ConciliationService conciliationService;

    @PostMapping("/nsu/{nsu}")
    public ResponseEntity<List<ConciliationResponseDTO>> conciliateByNsu(
            @PathVariable String nsu
    ) {
        List<ConciliationResponseDTO> response =
                conciliationService.conciliationByNsu(nsu);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/batch/{enterpriseId}")
    public ResponseEntity<List<ConciliationResponseDTO>> conciliateBatch(
            @PathVariable Long enterpriseId
    ) {
        List<ConciliationResponseDTO> response =
                conciliationService.conciliationBatch(enterpriseId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ConciliationResponseDTO>> findAll() {
        return ResponseEntity.ok(conciliationService.findAll());
    }

    @GetMapping("/summary")
    public ResponseEntity<List<ConciliationSummaryDTO>> findAllSummary() {
        return ResponseEntity.ok(conciliationService.findAllSummary());
    }

    @GetMapping("/divergences")
    public ResponseEntity<List<ConciliationResponseDTO>> findDivergences() {
        return ResponseEntity.ok(conciliationService.findDivergences());
    }

    @GetMapping("/enterprise/{enterpriseId}")
    public ResponseEntity<List<ConciliationResponseDTO>> findByEnterprise(
            @PathVariable Long enterpriseId
    ) {
        return ResponseEntity.ok(
                conciliationService.findByEnterprise(enterpriseId)
        );
    }
}

