package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class TelegramUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "identifier")
    private String identifier;

    @Getter
    @NonNull
    @Column(name = "telegramId")
    private String telegramId;

    @NonNull
    @Column(name = "telegramMessage")
    private String telegramMessage;


    public TelegramUser() {

    }

    public TelegramUser(@NonNull String identifier, @NonNull String telegramId, @NonNull String telegramMessage) {
        this.identifier = identifier;
        this.telegramId = telegramId;
        this.telegramMessage = telegramMessage;
    }
}