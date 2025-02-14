package com.example.demo.controller;

import com.example.demo.model.TelegramUser;
import com.example.demo.repo.TelegramUserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/TelegramUserController")
public class TelegramUserController {
    @Autowired
    private TelegramUserRepo telegramUserRepo;
    private static final Logger logger = LoggerFactory.getLogger(TelegramUserController.class);

    @PostMapping("/addTelegramUser")
    public String addTelegramUser(@RequestBody TelegramUser telegramUser) {
        telegramUserRepo.save(telegramUser);
        logger.info("Successfully added telegram user with id " + telegramUser.getTelegramId());
        return "Успішно додано телеграм користувача";
    }

    @PutMapping("/updateTelegramUser")
    public String updateTelegramUser(@RequestBody TelegramUser telegramUser) {
        if (!telegramUserRepo.existsById(telegramUser.getId())) {
            logger.info("Telegram user with id " + telegramUser.getId() + " does not exist");
            return "Користувача з айді " + telegramUser.getId() + " не існує";
        }
        TelegramUser updatedUser = telegramUserRepo.save(telegramUser);
        logger.info("Successfully updated telegram user with id " + updatedUser.getId());
        return "Успішно відредаговано телеграм користувача";
    }

    @PostMapping("/deleteTelegramUser")
    public String deleteTelegramUser(@RequestBody TelegramUser telegramUser) {
        if (!telegramUserRepo.existsById(telegramUser.getId())) {
            logger.info("Telegram user with id " + telegramUser.getId() + " does not exist");
            return "Користувача з айді " + telegramUser.getId() + " не існує";
        }
        telegramUserRepo.delete(telegramUser);
        logger.info("Successfully deleted telegram user with id " + telegramUser.getTelegramId());
        return "Успішно видалено телеграм користувача";
    }

    @GetMapping("/getTelegramUsers")
    public List<TelegramUser> getTelegramUsers(@RequestParam String identifier) {
        logger.info("User with indicator " + identifier + " found in the list of users telegram");
        return telegramUserRepo.findTelegramUsersByIdentifier(identifier);
    }
}