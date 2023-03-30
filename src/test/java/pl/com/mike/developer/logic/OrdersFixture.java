package pl.com.mike.developer.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class OrdersFixture {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private EntityManager em;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createOrders() {
//        OrderCreateData data = new OrderCreateData("Ewa", "Ogrod", "customerType", "md00001", group, driverId);
//        ordersService.create(data);
//
//        data = new OrderCreateData("Jan", "Kowalik", "customerType", "md00002", group, driverId);
//        ordersService.create(data);
//
//        data = new OrderCreateData("Adam", "Romański", "customerType", "md00003", group, driverId);
//        ordersService.create(data);
//
//        em.flush();

    }
}
