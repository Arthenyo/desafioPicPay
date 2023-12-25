package com.arthenyo.desafioPicPay.entity.controller;

import com.arthenyo.desafioPicPay.entity.DTO.TransferDTO;
import com.arthenyo.desafioPicPay.entity.Transfer;
import com.arthenyo.desafioPicPay.repository.UserRepository;
import com.arthenyo.desafioPicPay.service.TransferService;
import com.arthenyo.desafioPicPay.service.exception.TransferError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> makeTransfer(@RequestBody TransferDTO transferDTO) {
        try {
            Transfer transfer = convertToEntity(transferDTO);
            Transfer result = transferService.makeTransfer(transfer);
            return ResponseEntity.ok(result);
        } catch (TransferError e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private Transfer convertToEntity(TransferDTO transferDTO) {
        Transfer transfer = new Transfer();

        transfer.setPayer(userRepository.getReferenceById(transferDTO.getPayerId()));
        transfer.setPayee(userRepository.getReferenceById(transferDTO.getPayeeId()));
        transfer.setAmount(transferDTO.getAmount());

        return transfer;
    }
}