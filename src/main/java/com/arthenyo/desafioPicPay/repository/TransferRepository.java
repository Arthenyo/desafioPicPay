package com.arthenyo.desafioPicPay.repository;

import com.arthenyo.desafioPicPay.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer,Long> {
}
