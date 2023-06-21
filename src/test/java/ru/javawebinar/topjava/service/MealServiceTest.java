package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService service;

    @Test
    public void testGet() {
        Meal meal = service.get(BREAKFAST_OF_USER_ID, USER_ID);
        assertThat(meal).usingRecursiveComparison().isEqualTo(breakfastOfUser);
    }

    @Test
    public void testDelete() {
        service.delete(LUNCH_OF_USER_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(LUNCH_OF_USER_ID, USER_ID));
    }

    @Test
    public void testDeleteNotOwnedMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(LUNCH_OF_USER_ID, ADMIN_ID));
    }

    @Test
    public void testGetBetweenInclusive() {
        List<Meal> meals = service.getBetweenInclusive(LocalDate.of(2023, 6, 1),
                LocalDate.of(2023, 6, 2), USER_ID);
        assertThat(meals).containsExactlyInAnyOrder(breakfastOfUser, lunchOfUser, dinnerOfUser,
                breakfast2OfUser, lunch2OfUser, dinner2OfUser);
    }

    @Test
    public void testGetAll() {
        List<Meal> meals = service.getAll(ADMIN_ID);
        assertThat(meals).containsExactlyInAnyOrder(breakfastOfAdmin, lunchOfAdmin, dinnerOfAdmin,
                breakfast2OfAdmin, lunch2OfAdmin, dinner2OfAdmin);
    }

    @Test
    public void testUpdate() {
        Meal updated = MealTestData.getUpdated();
        service.update(updated, USER_ID);
        assertThat(service.get(LUNCH_OF_USER_ID, USER_ID)).usingRecursiveComparison().isEqualTo(MealTestData.getUpdated());
    }

    @Test
    public void testUpdateNotOwnedMeal() {
        assertThrows(NotFoundException.class, () -> service.update(lunchOfUser, ADMIN_ID));
    }

    @Test
    public void testCreate() {
        Meal created = service.create(MealTestData.getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = MealTestData.getNew();
        newMeal.setId(newId);
        assertThat(created).usingRecursiveComparison().isEqualTo(newMeal);
        assertThat(service.get(newId, USER_ID)).usingRecursiveComparison().isEqualTo(newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () -> service.create(new Meal(null,
                LocalDateTime.of(2023, 6, 1, 18, 0), "duplicate", 700), USER_ID));
    }
}