package tacos.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import tacos.persistence.entity.User;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    Mono<UserDetails> findByUsername(String username);
}
