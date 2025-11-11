package tacos.service;

import tacos.persistence.entity.TacoOrder;

public interface OrderMessagingService {
    void sendOrder(TacoOrder tacoOrder);
}
