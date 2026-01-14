package tacos.domain;

import org.springframework.context.ApplicationEvent;

public class TacoCreationEvent extends ApplicationEvent {

    public TacoCreationEvent(Object source) {
        super(source);
    }
}
