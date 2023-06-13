package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
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

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(this::saveTest);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        else if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        } else return null;
    }

    public Meal saveTest(Meal meal) {
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
    public boolean delete(int id, int userId) {
        try {
            if (userId == repository.get(id).getUserId()) {
                repository.remove(id);
                return true;
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }


    @Override
    public Meal get(int id, int userId) {
        try {
            if (userId == repository.get(id).getUserId()) {
                return repository.get(id);
            }
        } catch (NullPointerException e) {
            return null;
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getFiltered(userId, MIN, MAX);
    }

    @Override
    public Collection<Meal> getFiltered(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

}

