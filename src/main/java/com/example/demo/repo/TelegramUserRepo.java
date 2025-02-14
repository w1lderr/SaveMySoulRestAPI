package com.example.demo.repo;


import com.example.demo.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource
public interface TelegramUserRepo extends JpaRepository<TelegramUser, Long> {
    List<TelegramUser> findTelegramUsersByIdentifier(String identifier);
}