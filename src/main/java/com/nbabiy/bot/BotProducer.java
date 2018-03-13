package com.nbabiy.bot;

import com.nbabiy.domain.Pizza;
import org.telegram.telegrambots.TelegramBotsApi;

import javax.enterprise.inject.Produces;

public class BotProducer {

    @Produces @BotData
    private boolean start = false;


}
