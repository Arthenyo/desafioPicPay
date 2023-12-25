package com.arthenyo.desafioPicPay.entity.DTO;

import com.arthenyo.desafioPicPay.entity.User;
import com.arthenyo.desafioPicPay.enums.TypeUser;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

public class UserDTO {
    private Long id;
    @Size(min = 4,message = "Campo First Name deve ter no minimo 4 caracteres")
    @NotBlank(message = "Campo Requerido")
    private String firstName;
    @Size(min = 4,message = "Campo Last Name deve ter no minimo 4 caracteres")
    @NotBlank(message = "Campo Requerido")
    private String lastName;
    @CPF(message = "CPF invalido")
    @NotBlank(message = "Campo Requerido")
    private String cpf;
    @Email(message = "Email invalido")
    @NotBlank(message = "Campo Requerido")
    private String email;
    @NotNull(message = "Campo Requerido")
    private TypeUser typeUser;
    private BigDecimal balance;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String cpf, String email, TypeUser typeUser, BigDecimal balance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.email = email;
        this.typeUser = typeUser;
        this.balance = balance;
    }

    public UserDTO(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        cpf = entity.getCpf();
        email = entity.getEmail();
        typeUser = entity.getTypeUser();
        balance = entity.getBalance();
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
