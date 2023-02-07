package ru.netology.data;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class DataGenerator {

    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static data.Card getValidCard() {
        return new data.Card("4444 4444 4444 4441", getCurrentMonth(), getCurrentYear(), "Card Holder", "123");
    }

    public static data.Card getDeclinedCard() {
        return new data.Card("4444 4444 4444 4442", getCurrentMonth(), getCurrentYear(), "Card Holder", "123");
    }

    public static data.Card getFakeCard() {
        return new data.Card("4444 4444 4444 4449", getCurrentMonth(), getCurrentYear(), "Card Holder", "123");
    }

    public static data.Card getInvalidHolderCard() {
        return new data.Card("4444 4444 4444 4441", getCurrentMonth(), getCurrentYear(), "123456789Йцукенгшщзхъ!\"№;%:?*()123456789Йцукенгшщзхъ!\"№;%:?*()", "123");
    }

    public static data.Card getInvalidExpDateCard(int months, int years) {
        String month = LocalDate.now().minusMonths(months).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().minusYears(years).format(DateTimeFormatter.ofPattern("yy"));
        return new data.Card("4444 4444 4444 4441", month, year, "Card Holder", "123");
    }

}
