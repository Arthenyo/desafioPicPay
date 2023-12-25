package com.arthenyo.desafioPicPay.entity.controller;

import com.arthenyo.desafioPicPay.entity.DTO.UserDTO;
import com.arthenyo.desafioPicPay.entity.DTO.UserInsertDTO;
import com.arthenyo.desafioPicPay.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAllName(@RequestParam(value = "nome",defaultValue = "") String nome, Pageable pageable){
        Page<UserDTO> page = userService.findAllPage(nome,pageable);
        return ResponseEntity.ok().body(page);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }
    @PostMapping
    public ResponseEntity<UserDTO> insert(@RequestBody @Valid UserInsertDTO insertDTO){
        UserDTO dto = userService.insert(insertDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
    @PutMapping("{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id,@RequestBody @Valid UserInsertDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id,dto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
