package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {
    void create(Meal meal);

    Meal read(int id);

    void update(Meal meal);

    void delete(int id);

    List<Meal> getAll();
}
