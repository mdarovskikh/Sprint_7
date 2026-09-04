package ru.yandex.praktikum;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.yandex.praktikum.couriertests.CourierCreateTests;
import ru.yandex.praktikum.couriertests.CourierDeleteTests;
import ru.yandex.praktikum.couriertests.CourierLoginTests;
import ru.yandex.praktikum.ordertests.OrderCreateParameterizedTests;
import ru.yandex.praktikum.ordertests.OrderGetTests;
import ru.yandex.praktikum.ordertests.OrderListTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CourierCreateTests.class,
        CourierLoginTests.class,
        CourierDeleteTests.class,
        OrderCreateParameterizedTests.class,
        OrderListTest.class,
        OrderGetTests.class
})
public class AllTests {
}
