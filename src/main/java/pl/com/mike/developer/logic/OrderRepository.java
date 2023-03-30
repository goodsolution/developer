package pl.com.mike.developer.logic;

import org.springframework.data.repository.CrudRepository;

interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
