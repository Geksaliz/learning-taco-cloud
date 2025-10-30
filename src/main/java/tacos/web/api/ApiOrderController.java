package tacos.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tacos.persistence.entity.TacoOrder;
import tacos.persistence.repository.OrderRepository;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/api/orders", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ApiOrderController {
    private final OrderRepository orderRepository;

    @PutMapping(value = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TacoOrder> putOrder(@PathVariable Long orderId, @RequestBody TacoOrder newOrder) {
        if (orderRepository.findById(orderId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        newOrder.setId(orderId);

        return ResponseEntity.ok(orderRepository.save(newOrder));
    }

    @PatchMapping(path = "/{orderId}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<TacoOrder> patchOrder(@PathVariable Long orderId, @RequestBody TacoOrder patch) {
        final Optional<TacoOrder> optionalTacoOrder = orderRepository.findById(orderId);

        if (optionalTacoOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final TacoOrder order = optionalTacoOrder.get();

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

        return ResponseEntity.ok(orderRepository.save(order));
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (EmptyResultDataAccessException ignored) {}
    }
}
