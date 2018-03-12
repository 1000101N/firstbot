package com.nbabiy.rest;

import com.nbabiy.bot.PizzaBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("com/bot")
@Singleton
public class BotBean {

    private TelegramBotsApi botsApi;

    public BotBean() {
    }

    @GET
    @Path("start")
    public void startBot(){
        ApiContextInitializer.init();

        botsApi = new TelegramBotsApi();
        try {
            botsApi.registerBot(new PizzaBot());
        } catch (TelegramApiException tae){
            tae.printStackTrace();
        }
        System.out.println(botsApi.toString());

    }

    @GET
    @Path("say/{name}")
    @Produces("application/json")
    public String say(@PathParam("name") String name){
        return "Hello "+name;
    }
}
