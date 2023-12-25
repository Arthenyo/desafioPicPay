package com.arthenyo.desafioPicPay.entity.DTO;

import com.arthenyo.desafioPicPay.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserInsertDTO extends UserDTO{
    @NotBlank(message = "Campo Obrigatorio")
    @Size(min = 8,message = "deve ter no minimo 8 caracteres")
    private String senha;

    public UserInsertDTO() {
        super();
    }

    public String getSenha() {
        return senha;
    }
}
