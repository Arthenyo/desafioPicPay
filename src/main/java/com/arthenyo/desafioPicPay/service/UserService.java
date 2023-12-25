package com.arthenyo.desafioPicPay.service;

import com.arthenyo.desafioPicPay.entity.DTO.UserDTO;
import com.arthenyo.desafioPicPay.entity.DTO.UserInsertDTO;
import com.arthenyo.desafioPicPay.entity.User;
import com.arthenyo.desafioPicPay.repository.UserRepository;
import com.arthenyo.desafioPicPay.service.exception.DataBaseException;
import com.arthenyo.desafioPicPay.service.exception.ObjectNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<UserDTO> findAllPage(String nome,Pageable pageable){
        Page<User> page = userRepository.searchUser(nome,pageable);
        return page.map(UserDTO::new);
    }
    public UserDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new ObjectNotFound("Usuario não Encontrado"));
        return new UserDTO(user);
    }
    public UserDTO insert(UserInsertDTO insertDTO){
        User entity = new User();
        entityToDto(entity,insertDTO);
        entity.setPassword(insertDTO.getSenha());
        entity = userRepository.save(entity);
        return new UserDTO(entity);
    }
    public UserDTO update(Long id,UserInsertDTO insertDTO){
        try{
            User entity = userRepository.getReferenceById(id);
            entityToDto(entity,insertDTO);
            entity = userRepository.save(entity);
            return new UserDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ObjectNotFound("Usuario não encontrado" + id);
        }

    }
    public void delete(Long id){
        if(!userRepository.existsById(id)){
            throw new ObjectNotFound("Usuario nao Encontrado" + id);
        }
        try {
            userRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Não Foi possivel deletar o usuario %d, erro de integridade" + id);
        }
    }
    private void entityToDto(User entity, UserDTO dto){
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setCpf(dto.getCpf());
        entity.setEmail(dto.getEmail());
        entity.setTypeUser(dto.getTypeUser());
        entity.setBalance(dto.getBalance());
    }
}
