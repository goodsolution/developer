package pl.com.mike.developer.logic;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.mike.developer.Application;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Application.class)
public class OrdersServiceIT {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersFixture ordersFixture;


    @Ignore
    @Test
    public void shouldFindAnyOrdersShortWhenAllParamsInFilterAreFilledWithExixtingData() {
        // given
        ordersFixture.createOrders();
        OrdersFilter filter = new OrdersFilter("Ewa", "Ogrod", "md00001");

        // when
        List<OrderData> result = ordersService.findOrdersShort(filter, 1L, 100L);

        // expected
        assertThat(result).hasSize(1);
    }

    @Test
    public void shouldNotFindAnyOrdersShortWhenAllParamsInFilterAreNull() {
        // given
        ordersFixture.createOrders();
        OrdersFilter filter = new OrdersFilter(null, null, null);

        // when
        List<OrderData> result = ordersService.findOrdersShort(filter, 1L, 100L);

        // expected
        assertThat(result).hasSize(0);
    }

    @Test
    public void shouldNotFindAnyOrdersShortWhenAllParamsInFilterAreEmpty() {
        // given
        ordersFixture.createOrders();
        OrdersFilter filter = new OrdersFilter("", "", "");

        // when
        List<OrderData> result = ordersService.findOrdersShort(filter, 1L, 100L);

        // expected
        assertThat(result).hasSize(0);
    }
}
