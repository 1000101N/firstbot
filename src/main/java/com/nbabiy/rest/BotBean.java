package com.nbabiy.rest;

import com.nbabiy.bot.BotData;
import com.nbabiy.bot.PizzaBot;
import com.nbabiy.db.DrinkBean;
import com.nbabiy.db.PizzaBean;
import com.nbabiy.domain.Drink;
import com.nbabiy.domain.Pizza;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("bot")
@Singleton
public class BotBean {

    @Inject
    @BotData
    private boolean start;

    @Inject
    private DrinkBean drinkBean;

    @Inject
    private PizzaBean pizzaBean;

    PizzaBot pizzaBot;

    public BotBean() {
    }

//    static {
//        ApiContextInitializer.init();
//    }

    @GET
    @Path("start")
    @Produces("application/json")
    public String startBot() {
        ApiContextInitializer.init();


        if (!start) {
            pizzaBot = new PizzaBot();
//            pizzaBot.initData();
//            pizzaBot.initData();
            TelegramBotsApi botsApi = new TelegramBotsApi();
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

    @GET
    @Path("init")
    @Produces("application/json")
    public String init() {
        List<Pizza> pizzas = new ArrayList<>();

        pizzas.add(new Pizza("Піца Барбекю", "Соус / Сир Королівський / Салямі / Цибуля / Перець чілі / Кукурудза / \n 35 см, 565 гр.", 110f, true, "http://pizza-if.com/wp-content/uploads/Безіменний.png"));
        pizzas.add(new Pizza("Капрічіоза Класік", "Соус / Сир Королівський / Шинка / Печериці / Орегано / Італійські оливки / \n 35 см, 555 гр.", 118f, true, "http://pizza-if.com/wp-content/uploads/Капрічіоза-класссік.png"));
        pizzas.add(new Pizza("П’ять сирів", "Соус / Сир Королівський / Сир Моцарелла / Сир Пармезан / Сир Фета / Сир Горгонзола / \n 35 см, 535 гр", 145f, true, "http://pizza-if.com/wp-content/uploads/5-2.png"));
        pizzas.add(new Pizza("Піца Ібіца", "Соус / Сир Королівський / Курка / Креветки / Кукурудза / Орегано / Соус Песто / \n 35 см, 580 гр.", 169f, true, "http://pizza-if.com/wp-content/uploads/5-3.png"));

        for (Pizza p : pizzas) {
            pizzaBean.addPizza(p);
        }

        List<Drink> drinks = new ArrayList<>();

        drinks.add(new Drink("Мохіто (Власного приготування)", 17f, true, "http://pizza-if.com/wp-content/uploads/0022_mojito-png.png", 0.5f));
        drinks.add(new Drink("Сік яблучний", 23f, true, "http://pizza-if.com/wp-content/uploads/Без-имени-1-1.png", 0.3f));
        drinks.add(new Drink("Pepsi", 21f, true, "http://pizza-if.com/wp-content/uploads/44444.png", 1f));
        drinks.add(new Drink("Sandora ананасовий сік", 35, true, "http://pizza-if.com/wp-content/uploads/0005.png", 1f));

        for (Drink d : drinks) {
            drinkBean.addDrink(d);
        }

        return "Data was initialized.";
    }

    @GET
    @Path("getData")
    @Produces("application/json")
    public String getData() {
        List<Pizza> pizzas = pizzaBean.getAllPizza();

        List<Drink> drinks = drinkBean.getAllDrink();

        pizzaBot.setPizzas(pizzas);
        pizzaBot.setDrinks(drinks);

        return "Data was added.";
    }
}
