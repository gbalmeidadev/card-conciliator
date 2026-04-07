package com.conciliator.card_conciliator.Repository;

import com.conciliator.card_conciliator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.perfis WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByEnterpriseId(Long enterpriseId);
}
