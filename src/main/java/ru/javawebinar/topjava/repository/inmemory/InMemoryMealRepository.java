package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenHalfOpen;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
        MealsUtil.mealsForAnotherUser.forEach(meal -> this.save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (repository.get(meal.getId()).getUserId() == userId) {
            return repository.compute(meal.getId(), (id, oldMeal) -> meal);
        } else return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.getOrDefault(id, null);
        if (meal != null && userId == meal.getUserId()) {
            repository.remove(id);
            return true;
        } else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.getOrDefault(id, null);
        if (meal != null && userId == meal.getUserId()) {
            return meal;
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(userId, LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .filter(meal -> isBetweenHalfOpen(meal.getDate(), startDate, endDate))
                .filter(meal -> isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .sorted(comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

}

