package com.example.demo.service;

import com.example.demo.model.TelegramUser;
import com.example.demo.repo.TelegramUserRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelegramUserService {
    @Autowired
    private TelegramUserRepo telegramUserRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${telegram.bot.api.url}")
    private String BASE_URL;

    private final Map<Long, Integer> userMessageIds = new HashMap<>();
    private final Map<Long, Double> userLastLatitudes = new HashMap<>();
    private final Map<Long, Double> userLastLongitudes = new HashMap<>();

    public void sendLiveLocation(Double latitude, Double longitude, String uuid) {
        List<TelegramUser> users = telegramUserRepo.findTelegramUsersByIdentifier(uuid);

        for (TelegramUser telegramUser : users) {
            Long chatId = Long.valueOf(telegramUser.getTelegramId());
            try {
                Integer messageId = userMessageIds.get(chatId);
                Double lastLatitude = userLastLatitudes.get(chatId);
                Double lastLongitude = userLastLongitudes.get(chatId);

                if (messageId == null) {
                    String url = BASE_URL + "/sendLocation";

                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                            .queryParam("chat_id", chatId)
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude)
                            .queryParam("live_period", 86400); // live stream for 24 hours

                    String response = restTemplate.getForObject(builder.toUriString(), String.class);

                    JSONObject jsonResponse = new JSONObject(response);
                    messageId = jsonResponse.getJSONObject("result").getInt("message_id");

                    userMessageIds.put(chatId, messageId);
                    userLastLatitudes.put(chatId, latitude);
                    userLastLongitudes.put(chatId, longitude);

                    System.out.println("Live location started for user " + chatId + ". Message ID: " + messageId);

                } else {
                    if (latitude.equals(lastLatitude) && longitude.equals(lastLongitude)) {
                        System.out.println("Coordinates unchanged for user " + chatId + ". Skipping update.");
                        continue;
                    }

                    String url = BASE_URL + "/editMessageLiveLocation";

                    UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                            .queryParam("chat_id", chatId)
                            .queryParam("message_id", messageId)
                            .queryParam("latitude", latitude)
                            .queryParam("longitude", longitude);

                    restTemplate.getForObject(builder.toUriString(), String.class);

                    userLastLatitudes.put(chatId, latitude);
                    userLastLongitudes.put(chatId, longitude);

                    System.out.println("Live location updated for user " + chatId + " to: Latitude = " + latitude + ", Longitude = " + longitude);
                }
            } catch (Exception e) {
                System.err.println("Error while sending live location for user " + chatId + ": " + e.getMessage());
            }
        }
    }
}