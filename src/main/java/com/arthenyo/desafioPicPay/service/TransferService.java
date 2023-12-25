package com.arthenyo.desafioPicPay.service;

import com.arthenyo.desafioPicPay.entity.Transfer;
import com.arthenyo.desafioPicPay.entity.User;
import com.arthenyo.desafioPicPay.enums.TypeUser;
import com.arthenyo.desafioPicPay.repository.TransferRepository;
import com.arthenyo.desafioPicPay.repository.UserRepository;
import com.arthenyo.desafioPicPay.service.exception.InsufficientFundsException;
import com.arthenyo.desafioPicPay.service.exception.TransferError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Transfer makeTransfer(Transfer transfer) {
        User sender = userRepository.findById(transfer.getPayer().getId()).orElseThrow(() -> new RuntimeException("Usuário remetente não encontrado"));
        User receiver = userRepository.findById(transfer.getPayee().getId()).orElseThrow(() -> new RuntimeException("Usuário destinatário não encontrado"));

        if (sender.getTypeUser().equals(TypeUser.STORE)) {
            throw new TransferError("Usuário Logística não pode fazer transferência");
        }

        if (sender.getBalance().compareTo(transfer.getAmount()) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para transferência");
        }

        sender.setBalance(sender.getBalance().subtract(transfer.getAmount()));
        receiver.setBalance(receiver.getBalance().add(transfer.getAmount()));

        userRepository.save(sender);
        userRepository.save(receiver);

        Transfer savedTransfer = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> sendNotification(savedTransfer));

        return savedTransfer;
    }

    private void sendNotification(Transfer transfer) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String notificationServiceUrl = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

            ResponseEntity<String> response = restTemplate.getForEntity(notificationServiceUrl, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Falha ao enviar notificação. Código de status: " + response.getStatusCode());
            } else {
                System.out.println("Notificação enviada com sucesso. Resposta do serviço: " + response.getBody());
            }
        } catch (Exception e) {
            System.out.println("Erro ao comunicar com o serviço de notificação: " + e.getMessage());
        }
    }

}
