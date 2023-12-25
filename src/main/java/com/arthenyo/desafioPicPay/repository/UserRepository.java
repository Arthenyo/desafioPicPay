package com.arthenyo.desafioPicPay.repository;

import com.arthenyo.desafioPicPay.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT a FROM User a WHERE UPPER(TRIM(a.firstName)) LIKE UPPER(TRIM(CONCAT('%', :nome, '%')))")
    Page<User> searchUser(String nome, Pageable pageable);
}
