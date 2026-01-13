package tacos.persistence.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import tacos.persistence.entity.Taco;

@Repository
public interface TacoRepository extends ReactiveCrudRepository<Taco, Long> {
}
