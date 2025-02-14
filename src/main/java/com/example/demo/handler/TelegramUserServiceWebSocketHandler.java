package com.example.demo.handler;

import com.example.demo.service.TelegramUserService;
import org.json.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TelegramUserServiceWebSocketHandler extends TextWebSocketHandler {

    private final TelegramUserService telegramService;

    public TelegramUserServiceWebSocketHandler(TelegramUserService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        JSONObject json = new JSONObject(payload);

        Double latitude = json.getDouble("latitude");
        Double longitude = json.getDouble("longitude");
        String uuid = json.getString("uuid");

        telegramService.sendLiveLocation(latitude, longitude, uuid);
    }
}