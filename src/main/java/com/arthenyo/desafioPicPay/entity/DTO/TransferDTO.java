package com.arthenyo.desafioPicPay.entity.DTO;

import com.arthenyo.desafioPicPay.entity.Transfer;
import com.arthenyo.desafioPicPay.entity.User;

import java.math.BigDecimal;

public class TransferDTO {
    private Long payerId;
    private Long payeeId;
    private BigDecimal amount;

    public TransferDTO(Long payerId, Long payeeId, BigDecimal amount) {
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.amount = amount;
    }

    public TransferDTO(Transfer entity) {
        this.payerId = entity.getPayer().getId();
        this.payeeId = entity.getPayee().getId();
        this.amount = entity.getAmount();
    }

    // Getters

    public Long getPayerId() {
        return payerId;
    }

    public Long getPayeeId() {
        return payeeId;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}

