package pl.com.goodsolution.adviser.auth;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
}
