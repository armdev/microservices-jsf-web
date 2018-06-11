package io.project.mongo.repositories;

import io.project.mongo.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 *
 * @author armen
 */
@Component
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
