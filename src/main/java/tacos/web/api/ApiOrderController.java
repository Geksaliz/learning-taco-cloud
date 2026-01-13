package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import tacos.persistence.entity.TacoOrder;
import tacos.persistence.repository.OrderRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/orders", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiOrderController {
    private final OrderRepository orderRepository;

    @PutMapping(value = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public Mono<TacoOrder> putOrder(@PathVariable Long orderId, @RequestBody TacoOrder newOrder) {
        return orderRepository.findById(orderId)
                .map(TacoOrder::getId)
                .flatMap(id -> {
                    newOrder.setId(id);
                    return orderRepository.save(newOrder);
                });
    }

    @PatchMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public Mono<TacoOrder> patchOrder(@PathVariable Long orderId, @RequestBody TacoOrder patch) {
        return orderRepository.findById(orderId)
                .flatMap(order -> {
                    if (patch.getDeliveryName() != null) {
                        order.setDeliveryName(patch.getDeliveryName());
                    }
                    if (patch.getDeliveryCity() != null) {
                        order.setDeliveryCity(patch.getDeliveryCity());
                    }
                    if (patch.getDeliveryState() != null) {
                        order.setDeliveryState(patch.getDeliveryState());
                    }
                    if (patch.getDeliveryZip() != null) {
                        order.setDeliveryZip(patch.getDeliveryZip());
                    }
                    if (patch.getCcNumber() != null) {
                        order.setCcNumber(patch.getCcNumber());
                    }
                    if (patch.getCcExpiration() != null) {
                        order.setCcExpiration(patch.getCcExpiration());
                    }
                    if (patch.getCcCW() != null) {
                        order.setCcCW(patch.getCcCW());
                    }

                    return orderRepository.save(order);
                });
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {
        }
    }
}
