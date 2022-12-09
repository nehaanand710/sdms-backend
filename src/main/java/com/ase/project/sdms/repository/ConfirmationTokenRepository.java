package com.ase.project.sdms.repository;

import com.ase.project.sdms.domain.ConfirmationToken;
import com.ase.project.sdms.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {

    ConfirmationToken findByConfirmationToken(String confirmationToken);

    ConfirmationToken findByUser(User user);

    ConfirmationToken deleteByConfirmationToken(String token);

}

