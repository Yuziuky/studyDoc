package com.example.repository;

import com.example.entity.AccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountBalanceRepository extends JpaRepository<AccountBalance, Long> {
    AccountBalance findByUserId(long userId) throws Exception;
}
