package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int BREAKFAST_OF_USER_ID = START_SEQ + 3;
    public static final int LUNCH_OF_USER_ID = START_SEQ + 4;
    public static final int DINNER_OF_USER_ID = START_SEQ + 5;
    public static final int BREAKFAST_2_OF_USER_ID = START_SEQ + 6;
    public static final int LUNCH_2_OF_USER_ID = START_SEQ + 7;
    public static final int DINNER_2_OF_USER_ID = START_SEQ + 8;
    public static final int BREAKFAST_OF_ADMIN_ID = START_SEQ + 9;
    public static final int LUNCH_OF_ADMIN_ID = START_SEQ + 10;
    public static final int DINNER_OF_ADMIN_ID = START_SEQ + 11;
    public static final int BREAKFAST_2_OF_ADMIN_ID = START_SEQ + 12;
    public static final int LUNCH_2_OF_ADMIN_ID = START_SEQ + 13;
    public static final int DINNER_2_OF_ADMIN_ID = START_SEQ + 14;

    public static final Meal breakfastOfUser = new Meal(BREAKFAST_OF_USER_ID,
            LocalDateTime.of(2023, 6, 1, 8, 0), "Breakfast of user", 350);
    public static final Meal lunchOfUser = new Meal(LUNCH_OF_USER_ID,
            LocalDateTime.of(2023, 6, 1, 12, 30), "Lunch of user", 500);
    public static final Meal dinnerOfUser = new Meal(DINNER_OF_USER_ID,
            LocalDateTime.of(2023, 6, 1, 18, 0), "Dinner of user", 700);
    public static final Meal breakfast2OfUser = new Meal(BREAKFAST_2_OF_USER_ID,
            LocalDateTime.of(2023, 6, 2, 7, 30), "Breakfast of user", 1000);
    public static final Meal lunch2OfUser = new Meal(LUNCH_2_OF_USER_ID,
            LocalDateTime.of(2023, 6, 2, 13, 0), "Lunch of user", 1000);
    public static final Meal dinner2OfUser = new Meal(DINNER_2_OF_USER_ID,
            LocalDateTime.of(2023, 6, 2, 19, 30), "Dinner of user", 800);
    public static final Meal breakfastOfAdmin = new Meal(BREAKFAST_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 1, 9, 30), "Breakfast of admin", 400);
    public static final Meal lunchOfAdmin = new Meal(LUNCH_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 1, 13, 0), "Lunch of admin", 1000);
    public static final Meal dinnerOfAdmin = new Meal(DINNER_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 1, 19, 0), "Dinner of admin", 1000);
    public static final Meal breakfast2OfAdmin = new Meal(BREAKFAST_2_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 2, 8, 0), "Breakfast of admin", 350);
    public static final Meal lunch2OfAdmin = new Meal(LUNCH_2_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 2, 12, 30), "Lunch of admin", 500);
    public static final Meal dinner2OfAdmin = new Meal(DINNER_2_OF_ADMIN_ID,
            LocalDateTime.of(2023, 6, 2, 18, 30), "Dinner of admin", 700);


    public static Meal getUpdated() {
        Meal updated = new Meal(lunchOfUser);
        updated.setDateTime(LocalDateTime.of(2023, 6, 1, 12, 35));
        updated.setDescription("updated meal");
        updated.setCalories(600);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, 6, 1, 10, 0), "new meal", 500);
    }

}
