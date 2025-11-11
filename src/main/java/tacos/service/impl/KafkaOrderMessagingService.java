package tacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tacos.service.OrderMessagingService;
import tacos.persistence.entity.TacoOrder;

@Service
public class KafkaOrderMessagingService implements OrderMessagingService {
    private final KafkaTemplate<String, TacoOrder> kafkaTemplate;

    @Autowired
    public KafkaOrderMessagingService(KafkaTemplate<String, TacoOrder> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(TacoOrder tacoOrder) {
        kafkaTemplate.send("taco-cloud.orders.topic", tacoOrder);
    }
}
