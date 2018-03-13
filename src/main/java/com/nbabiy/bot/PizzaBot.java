package com.nbabiy.bot;

import com.nbabiy.domain.*;
import org.telegram.telegrambots.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;


public class PizzaBot extends TelegramLongPollingBot {

    private ArrayList<Pizza> pizzas;
    private ArrayList<Drink> drinks;

    private boolean order_menu = false;


    final private String GREETING = "Вас вітає VINTAGE_PIZZA_BOT";
    final private String MAIN_MENU = "Головне меню";

    final private String NEW_ORDER = "Нове замовлення";
    final private String CATEGORY = ".             Категорії               .";
    final private String PIZZA_C = ".               Піцци              .";
    final private String DRINK_C = ".               Напої             .";
    final private String SUSHI_C = ".               Суші                .";

    final private String ALL_LIST = "Список";

    final private String MY_ORDERS = "Мої замовлення";
    final private String ACTIVE_ORDER = "Активні";
    final private String DONE_ORDER = "Виконані";
    final private String YOUR_ORDER = "Ваше замовлення";

    final private String CART = "Кошик";
    final private String ADD_ONE = ":heavy_plus_sign:";
    final private String REMOVE_ONE = ":heavy_minus_sign:";
    final private String DELETE_ALL = ":heavy_multiplication_x:";
    final private String TOTAL = "ЗАГАЛОМ";

    final private String BACK = "НАЗАД";
    final private String NEXT = "ВПЕРЕД";
    final private String INDEX_OUT_OF_RANGE = "Requested index is out of range!";

    final private String MAIN_TAG = "main";
    final private String MAIN_NEW_ORDER_TAG = "new_order";
    final private String MAIN_MY_ORDERS_TAG = "my_orders";
    final private String CATEGORY_TAG = "category";
    final private String PIZZA_TAG = "pizza";
    final private String DRINK_TAG = "drink";
    final private String SUSHI_TAG = "sushi";

    final private String CART_TAG = "cart";

    final private String BACK_TAG = "back";
    final private String NEXT_TAG = "next";
    final private String ITEM_TAG = "item";
    final private String ALL_LIST_TAG = "all_list";

    private HashMap<Long, Order> orders;


    public PizzaBot() {
        initData();
    }

    private void initData() {
        orders = new HashMap<>();
        pizzas = new ArrayList<>();

        pizzas.add(new Pizza("Піца Барбекю", "Соус / Сир Королівський / Салямі / Цибуля / Перець чілі / Кукурудза / \n 35 см, 565 гр.", 110f, true, "http://pizza-if.com/wp-content/uploads/Безіменний.png"));
        pizzas.add(new Pizza("Капрічіоза Класік", "Соус / Сир Королівський / Шинка / Печериці / Орегано / Італійські оливки / \n 35 см, 555 гр.", 118f, true, "http://pizza-if.com/wp-content/uploads/Капрічіоза-класссік.png"));
        pizzas.add(new Pizza("П’ять сирів", "Соус / Сир Королівський / Сир Моцарелла / Сир Пармезан / Сир Фета / Сир Горгонзола / \n 35 см, 535 гр", 145f, true, "http://pizza-if.com/wp-content/uploads/5-2.png"));
        pizzas.add(new Pizza("Піца Ібіца", "Соус / Сир Королівський / Курка / Креветки / Кукурудза / Орегано / Соус Песто / \n 35 см, 580 гр.", 169f, true, "http://pizza-if.com/wp-content/uploads/5-3.png"));

        drinks = new ArrayList<>();

        drinks.add(new Drink("Мохіто (Власного приготування)", 17f, true, "http://pizza-if.com/wp-content/uploads/0022_mojito-png.png", 0.5f));
        drinks.add(new Drink("Сік яблучний", 23f, true, "http://pizza-if.com/wp-content/uploads/Без-имени-1-1.png", 0.3f));
        drinks.add(new Drink("Pepsi", 21f, true, "http://pizza-if.com/wp-content/uploads/44444.png", 1f));
        drinks.add(new Drink("Sandora ананасовий сік", 35, true, "http://pizza-if.com/wp-content/uploads/0005.png", 1f));
    }


    @Override
    public String getBotToken() {
        return BotConfig.PIZZA_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.PIZZA_USER;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message message = update.getMessage();
            if (message.hasText()) {

                String input = message.getText();
                if (input.equals("/start")) {

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());

                    sendMessage.setText(MAIN_MENU);
                    sendMessage.setReplyMarkup(this.getStartView());

                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                }
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackquery = update.getCallbackQuery();
            String[] data = callbackquery.getData().split(":");

            InlineKeyboardMarkup markup = null;

            EditMessageText editMarkup = new EditMessageText();
            editMarkup.setChatId(callbackquery.getMessage().getChatId().toString());
            editMarkup.setInlineMessageId(callbackquery.getInlineMessageId());
//            editMarkup.enableMarkdown(true);
            editMarkup.setMessageId(callbackquery.getMessage().getMessageId());

            boolean getOrder = false;
//            System.out.println(data[0]);
//            System.out.println(data[1]);
//            System.out.println();
            switch (data[0]) {
                case MAIN_TAG:
                    switch (data[1]) {
                        case MAIN_NEW_ORDER_TAG:
                            markup = this.getNewOrder();
                            editMarkup.setText(CATEGORY);
                            editMarkup.setReplyMarkup(markup);
                            try {
                                execute(editMarkup);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case MAIN_MY_ORDERS_TAG:
                            break;
                    }
                    break;
                case CATEGORY_TAG:
                    switch (data[1]) {
                        case PIZZA_TAG:
                            markup = this.getCategoryItems(CategoryOfFood.PIZZA);
                            editMarkup.setText(PIZZA_C);
                            editMarkup.setReplyMarkup(markup);
                            try {
                                execute(editMarkup);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case DRINK_TAG:
                            markup = this.getCategoryItems(CategoryOfFood.DRINK);
                            editMarkup.setText(DRINK_C);
                            editMarkup.setReplyMarkup(markup);
                            try {
                                execute(editMarkup);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                        case SUSHI_TAG:
                            break;
                        case CART_TAG:
                            break;
                        case MAIN_TAG:
                            markup = this.getStartView();
                            editMarkup.setText(MAIN_MENU);
                            editMarkup.setReplyMarkup(markup);
                            try {
                                execute(editMarkup);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
                case PIZZA_TAG:
                    System.out.println("ind" + Integer.parseInt(data[1]));
                    markup = this.getGalleryView(Integer.parseInt(data[1]), -2, 0, PIZZA_TAG);
                    Pizza p = this.pizzas.get(Integer.parseInt(data[1]));
                    editMarkup.setText(p.getName() + "\n" + p.getDescription() + "\n" + p.getPhoto());
                    editMarkup.setReplyMarkup(markup);
                    try {
                        execute(editMarkup);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
                case DRINK_TAG:
                    System.out.println("ind" + Integer.parseInt(data[1]));
                    markup = this.getGalleryView(Integer.parseInt(data[1]), -2, 0, DRINK_TAG);
                    Drink d = this.drinks.get(Integer.parseInt(data[1]));
                    editMarkup.setText(d.getName() + "\n" + d.getVolume() + "\n" + d.getPhoto());
                    editMarkup.setReplyMarkup(markup);
                    try {
                        execute(editMarkup);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
                case ITEM_TAG:
                    int index = Integer.parseInt(data[2]);

                    if (data[1].equals(BACK_TAG)) {
                        markup = this.getGalleryView(Integer.parseInt(data[2]), 1, callbackquery.getMessage().getChatId(), data[3]);
                        if (index >= 0) {
                            index--;
                        }

                        switch (data[3]) {
                            case PIZZA_TAG:
                                if(index<0){
                                    index = pizzas.size()-1;
                                }
                                Pizza p1 = this.pizzas.get(index);
                                editMarkup.setText(p1.getName() + "\n" + p1.getDescription() + "\n" + p1.getPhoto());
                                editMarkup.setReplyMarkup(markup);
                                break;
                            case DRINK_TAG:
                                if(index<0){
                                    index = drinks.size()-1;
                                }
                                Drink d1 = this.drinks.get(index);
                                editMarkup.setText(d1.getName() + "\n" + d1.getVolume() + " л \n" + d1.getPhoto());
                                editMarkup.setReplyMarkup(markup);
                                break;
                        }

                    } else if (data[1].equals(NEXT_TAG)) {
                        markup = this.getGalleryView(Integer.parseInt(data[2]), 2, callbackquery.getMessage().getChatId(), data[3]);

                        switch (data[3]) {
                            case PIZZA_TAG:
                                if (index <= this.pizzas.size() - 1) {
                                    index++;
                                }
                                if (index > this.pizzas.size() - 1) {
                                    index = 0;
                                }
                                Pizza p1 = this.pizzas.get(index);
                                editMarkup.setText(p1.getName() + "\n" + p1.getDescription() + "\n" + p1.getPhoto());
                                editMarkup.setReplyMarkup(markup);
                                break;
                            case DRINK_TAG:
                                if (index <= this.drinks.size() - 1) {
                                    index++;
                                }
                                if (index > this.drinks.size() - 1) {
                                    index = 0;
                                }
                                Drink d1 = this.drinks.get(index);
                                editMarkup.setText(d1.getName() + "\n" +    d1.getVolume() + " л\n" + d1.getPhoto());
                                editMarkup.setReplyMarkup(markup);
                                break;
                        }

                    } else if (data[1].equals(ALL_LIST_TAG)) {
                        switch (data[3]) {
                            case PIZZA_TAG:
                                markup = this.getCategoryItems(CategoryOfFood.PIZZA);
                                editMarkup.setText(PIZZA_C);
                                editMarkup.setReplyMarkup(markup);
                                break;
                            case DRINK_TAG:
                                markup = this.getCategoryItems(CategoryOfFood.DRINK);
                                editMarkup.setText(DRINK_C);
                                editMarkup.setReplyMarkup(markup);
                        }

                    }

                    if (markup == null) {
                        try {
                            this.sendAnswerCallbackQuery(INDEX_OUT_OF_RANGE, false, callbackquery);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            execute(editMarkup);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
            }


//            if (data[0].equals("pizza")) {
//
//                System.out.println(orders);
//                int index = Integer.parseInt(data[2]);
//
//                if (data[1].equals("list")) {
//                    markup = this.getGalleryView(0, -1, callbackquery.getMessage().getChatId());
//
//                } else if (data[1].equals("back")) {
//                    markup = this.getGalleryView(Integer.parseInt(data[2]), 1, callbackquery.getMessage().getChatId());
//                    if (index > 0) {
//                        index--;
//                    }
//                } else if (data[1].equals("next")) {
//                    markup = this.getGalleryView(Integer.parseInt(data[2]), 2, callbackquery.getMessage().getChatId());
//                    if (index < this.pizzas.size() - 1) {
//                        index++;
//                    }
//                } else if (data[1].equals("add")) {
//                    try {
//                        this.sendAnswerCallbackQuery("Pizza was added.", false, callbackquery);
//                        if (orders.containsKey(callbackquery.getMessage().getChatId())) {
//                            Order o = orders.get(callbackquery.getMessage().getChatId());
//                            List<Item> it = o.getItems();
//                            it.add(this.pizzas.get(Integer.parseInt(data[2])));
//                            o.setItems(it);
//                            orders.replace(callbackquery.getMessage().getChatId(), o);
//                        } else {
//                            Order o = new Order();
//                            List<Item> it = new ArrayList<>();
//                            it.add(this.pizzas.get(Integer.parseInt(data[2])));
//                            o.setItems(it);
//                            orders.put(callbackquery.getMessage().getChatId(), o);
//                        }
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                } else if (data[1].equals("order")) {
//                    markup = getOrder(callbackquery.getMessage().getChatId());
//                    getOrder = true;
//
//                }
//
//                if (markup == null) {
//                    try {
//                        this.sendAnswerCallbackQuery(INDEX_OUT_OF_RANGE, false, callbackquery);
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//
//
//                    if (getOrder) {
//
//                        if (orders.containsKey(callbackquery.getMessage().getChatId())) {
//                            String zakaz = orders.get(callbackquery.getMessage().getChatId()).getItems().toString();
//                            editMarkup.setText(zakaz);
//                        } else {
//                            editMarkup.setText("Nothing");
//                        }
//
//                    } else {
//
//                        editMarkup.setText(this.pizzas.get(index).getName() +
//                                "\n" + this.pizzas.get(index).getDescription() +
//                                "\n" + this.pizzas.get(index).getPrice() + " grn" +
//                                "\n" + this.pizzas.get(index).getPhoto());
//
//                    }
//
//
//                }
//            }


        }

    }

    private void sendAnswerCallbackQuery(String text, boolean alert, CallbackQuery callbackquery) throws TelegramApiException {
        AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
        answerCallbackQuery.setCallbackQueryId(callbackquery.getId());
        answerCallbackQuery.setShowAlert(alert);
        answerCallbackQuery.setText(text);
        answerCallbackQuery(answerCallbackQuery);
    }

    private InlineKeyboardMarkup getStartView() {

        InlineKeyboardMarkup startMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(new InlineKeyboardButton().setText(NEW_ORDER).setCallbackData(MAIN_TAG + ":" + MAIN_NEW_ORDER_TAG));

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(new InlineKeyboardButton().setText(MY_ORDERS).setCallbackData(MAIN_TAG + ":" + MAIN_MY_ORDERS_TAG));

        rowsInline.add(row1);
        rowsInline.add(row2);

        startMarkup.setKeyboard(rowsInline);

        return startMarkup;
    }

    private InlineKeyboardMarkup getNewOrder() {

        InlineKeyboardMarkup newOrderMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        row1.add(new InlineKeyboardButton().setText(PIZZA_C).setCallbackData(CATEGORY_TAG + ":" + PIZZA_TAG));

        List<InlineKeyboardButton> row2 = new ArrayList<>();
        row2.add(new InlineKeyboardButton().setText(DRINK_C).setCallbackData(CATEGORY_TAG + ":" + DRINK_TAG));

//        List<InlineKeyboardButton> row3 = new ArrayList<>();
//        row3.add(new InlineKeyboardButton().setText(SUSHI_C).setCallbackData(CATEGORY_TAG + ":" + SUSHI_TAG));

        List<InlineKeyboardButton> row4 = new ArrayList<>();
        row4.add(new InlineKeyboardButton().setText(CART).setCallbackData(CATEGORY_TAG + ":" + CART_TAG));

        List<InlineKeyboardButton> row5 = new ArrayList<>();
        row5.add(new InlineKeyboardButton().setText(MAIN_MENU).setCallbackData(CATEGORY_TAG + ":" + MAIN_TAG));

        rowsInline.add(row1);
        rowsInline.add(row2);
//        rowsInline.add(row3);
        rowsInline.add(row4);
        rowsInline.add(row5);

        newOrderMarkup.setKeyboard(rowsInline);

        return newOrderMarkup;
    }

    private InlineKeyboardMarkup getCategoryItems(CategoryOfFood category) {

        InlineKeyboardMarkup itemsMarkup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(new InlineKeyboardButton().setText(CATEGORY).setCallbackData(MAIN_TAG + ":" + MAIN_NEW_ORDER_TAG));
        rowsInline.add(row);

        switch (category) {
            case PIZZA:
                for (int i = 0; i < this.pizzas.size(); i++) {
                    Pizza p = this.pizzas.get(i);
                    List<InlineKeyboardButton> row_item = new ArrayList<>();
                    row_item.add(new InlineKeyboardButton().setText(p.getName() + " | " + p.getPrice()).setCallbackData(PIZZA_TAG + ":" + i));
                    rowsInline.add(row_item);
                }
                break;
            case DRINK:
                for (int i = 0; i < this.pizzas.size(); i++) {
                    Drink d = this.drinks.get(i);
                    List<InlineKeyboardButton> row_item = new ArrayList<>();
                    row_item.add(new InlineKeyboardButton().setText(d.getName() + " | " + d.getPrice() + "("+d.getVolume()+"л)").setCallbackData(DRINK_TAG + ":" + i));
                    rowsInline.add(row_item);
                }
                break;
            case SUSHI:
                break;
        }

        itemsMarkup.setKeyboard(rowsInline);

        return itemsMarkup;
    }

    private InlineKeyboardMarkup getGalleryView(int index, int action, long chatId, String type) {
        /*
         * action = 1 -> back
		 * action = 2 -> next
		 * action = -1 -> nothing
		 */
        int size = -1;

        switch (type){
            case PIZZA_TAG:
                size = this.pizzas.size()-1;
                break;
            case DRINK_TAG:
                size = this.drinks.size()-1;
                break;
            case SUSHI_TAG:
//                index
                break;
        }

        if(action != -2) {
            if (action == 1 && index > 0) {
                index--;
            } else if ((action == 1 && index <= 0)) {
                index = size;
            } else if (action == 2 && index > size - 1) {
                index = 0;
            } else if (action == 2) {
                index++;
            }
        }

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        switch (type){
            case PIZZA_TAG:
                rowInline.add(new InlineKeyboardButton().setText("Додати - " + this.pizzas.get(index).getPrice()).setCallbackData(ITEM_TAG + ":"));
                break;
            case DRINK_TAG:
                rowInline.add(new InlineKeyboardButton().setText("Додати - " + this.drinks.get(index).getPrice()+"("+this.drinks.get(index).getVolume()+"л)").setCallbackData(ITEM_TAG+":"));
                break;
        }


        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline2.add(new InlineKeyboardButton().setText(BACK).setCallbackData(ITEM_TAG + ":" + BACK_TAG + ":" + index + ":" + type));
        rowInline2.add(new InlineKeyboardButton().setText(NEXT).setCallbackData(ITEM_TAG + ":" + NEXT_TAG + ":" + index + ":" + type));

        List<InlineKeyboardButton> rowInline3 = new ArrayList<>();
        rowInline3.add(new InlineKeyboardButton().setText(CART).setCallbackData(ITEM_TAG + ":" + CART_TAG + ":" + -2 + ":" + type));

        List<InlineKeyboardButton> rowInline4 = new ArrayList<>();
        rowInline4.add(new InlineKeyboardButton().setText(ALL_LIST).setCallbackData(ITEM_TAG + ":" + ALL_LIST_TAG + ":" + -2 + ":" + type));

        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        rowsInline.add(rowInline3);
        rowsInline.add(rowInline4);

        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }


    private InlineKeyboardMarkup getOrder(Long chatId) {
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Замовити").setCallbackData("pizza:purchase:" + 1));
        rowInline.add(new InlineKeyboardButton().setText("Назад").setCallbackData("pizza:list:" + 1));

        rowsInline.add(rowInline);

        markup.setKeyboard(rowsInline);

        return markup;
    }

}
