package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Long> {


    Optional<Enterprise> findByCnpj(String cnpj);

    boolean existsByCnpj(String cnpj);

    List<Enterprise> findByActiveTrue();

    List<Enterprise> findByFantasyNameContainingIgnoreCase (String fantasyName);

    List<Enterprise> findBySocialReasonContainingIgnoreCase(String socialReason);

    @Query("""
           SELECT e
           FROM Enterprise e
           LEFT JOIN FETCH e.users
           WHERE e.id = :id
           """)
    Optional<Enterprise> findEnterpriseWithUsers(Long id);
}
