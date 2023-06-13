package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.time.LocalDateTime.MAX;
import static java.time.LocalDateTime.MIN;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        else if (meal.getUserId() == authUserId()) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else return null;
    }

    @Override
    public boolean delete(int id) {
        try {
            if (authUserId() == repository.get(id).getUserId()) {
                repository.remove(id);
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }


    @Override
    public Meal get(int id) {
        try {
            if (authUserId() == repository.get(id).getUserId()) {
                return repository.get(id);
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll() {
        return getFiltered(MIN, MAX);
    }

    public Collection<Meal> getFiltered(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.values()
                .stream()
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

}

