package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsInMemory implements MealsRepository {
    private static MealsInMemory mealsInMemory;
    private static List<Meal> meals = new ArrayList<>();
//    private static CopyOnWriteArrayList<Meal> meals = new CopyOnWriteArrayList<>();
    private static final AtomicInteger count = new AtomicInteger();

    private MealsInMemory() {
        meals = Arrays.asList(
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(count.incrementAndGet(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
    }

    public static MealsInMemory getInstance() {
        if (mealsInMemory == null) {
            mealsInMemory = new MealsInMemory();
        }
        return mealsInMemory;
    }


    @Override
    public void create(Meal meal) {
        meals.add(new Meal(count.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
    }

    @Override
    public Meal read(int id) {
        return meals.stream().filter(mealToRead -> mealToRead.getId() == id).findFirst().get();
    }

    @Override
    public void update(Meal meal) {
        meals.remove(meal);
        meals.add(meal);
    }

    @Override
    public void delete(int id) {
        meals.remove(meals.stream().filter(mealToRemove -> mealToRemove.getId() == id).findFirst().get());
    }

    @Override
    public List<Meal> getAll() {
        return meals;
    }

}
