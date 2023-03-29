package pl.com.goodsolution.adviser.logic;

import org.springframework.data.repository.CrudRepository;

interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
