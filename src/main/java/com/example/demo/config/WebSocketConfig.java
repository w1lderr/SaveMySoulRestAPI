package com.example.demo.config;

import com.example.demo.handler.TelegramUserServiceWebSocketHandler;
import com.example.demo.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private TelegramUserService telegramService;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new TelegramUserServiceWebSocketHandler(telegramService), "/ws/location")
                .setAllowedOrigins("*");
    }
}