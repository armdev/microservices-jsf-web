package io.project.mongo.repositories;

import io.project.mongo.domain.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 *
 * @author armen
 */
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Mono<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);

}
