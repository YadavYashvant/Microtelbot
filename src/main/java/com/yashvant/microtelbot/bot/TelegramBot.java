package com.yashvant.microtelbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message incomingMessage = update.getMessage();
            Long chatId = incomingMessage.getChatId();

            if (incomingMessage.hasText()) {
                String text = incomingMessage.getText();

                switch (text) {
                    case "/start":
                        sendMessage(chatId, "Hello! I am " + botName + ". I am here to help you, type /help to see the list of commands.");
                        break;
                    case "/help":
                        sendMessage(chatId, "Here are the list of commands: \n /start - Start the bot \n /help - List of commands \n /echo - Echo the message \n /time - Get the current time");
                        break;
                    case "/echo":
                        sendMessage(chatId, "You said: " + text);
                        break;
                    case "/time":
                        sendMessage(chatId, "The current time is: " + System.currentTimeMillis());
                        break;
                    default:
                        sendMessage(chatId, "I am sorry, I do not understand that command.");
                        break;
                }
            } else if (incomingMessage.hasSticker()) {
                sendMessage(chatId, "Nice sticker!");
            } else {
                sendMessage(chatId, "Invalid message. Please send a text message.");
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMessage(Long chatId, String sentMessage) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sentMessage);

        try {
            execute(message);
        } catch (Exception e) {
            log.error("Failed to send message", e);
        }
    }
}