package com.nbabiy.rest;

import com.nbabiy.bot.BotData;
import com.nbabiy.bot.PizzaBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("bot")
@Singleton
public class BotBean {

    @Inject
    @BotData
    private boolean start;

    //    @Inject @BotData
    private PizzaBot pizzaBot;

    //    @Inject @BotData
    private TelegramBotsApi botsApi;

    public BotBean() {
    }

    static {
        ApiContextInitializer.init();
    }

    @GET
    @Path("start")
    @Produces("application/json")
    public String startBot() {
        if (!start) {


            pizzaBot = new PizzaBot();
            botsApi = new TelegramBotsApi();
            try {
                botsApi.registerBot(pizzaBot);
                start = true;
            } catch (TelegramApiException tae) {
                tae.printStackTrace();
            }
            return botsApi.toString();

        } else {
            return "Bot has already been started.";
        }

    }

    @GET
    @Path("say/{name}")
    @Produces("application/json")
    public String say(@PathParam("name") String name) {
        return "Hello " + name;
    }
}
