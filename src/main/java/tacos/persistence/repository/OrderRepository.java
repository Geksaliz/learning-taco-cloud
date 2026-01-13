package tacos.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tacos.persistence.entity.TacoOrder;
import tacos.persistence.entity.User;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<TacoOrder, Long> {
    Flux<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
