package com.mangoslr.application.repositorios;


import com.mangoslr.application.model.PasswordResetToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepository extends CrudRepository<PasswordResetToken,Long> {

    PasswordResetToken findByToken(String token);
}
