package com.yashvant.microtelbot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage()){
            Message incomingMessage = update.getMessage();
            Long chatId = incomingMessage.getChatId();

            if(incomingMessage.hasText()){
                String text = incomingMessage.getText();

                if(text.equals("/start")){
                    sendMessage(chatId, "Hello! I am mobotronst. I am here to help you.");
                } else {
                    sendMessage(chatId, "I am sorry, I do not understand that command.");
                }
            }else{
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

    public void sendMessage(Long chatId, String sentMessage){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(sentMessage);

        try{
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}