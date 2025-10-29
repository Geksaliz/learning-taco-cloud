package tacos.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tacos.persistence.entity.TacoOrder;
import tacos.persistence.entity.User;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);
}
