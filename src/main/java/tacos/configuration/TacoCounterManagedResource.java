package tacos.configuration;

import org.springframework.context.event.EventListener;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Service;
import tacos.domain.TacoCreationEvent;
import tacos.persistence.repository.TacoRepository;

import javax.management.Notification;
import java.util.concurrent.atomic.AtomicLong;

@Service
@ManagedResource
public class TacoCounterManagedResource implements NotificationPublisherAware {

    private final AtomicLong counter = new AtomicLong(0);;
    private NotificationPublisher publisher;


    public TacoCounterManagedResource(TacoRepository repository) {
        repository.count()
                .subscribe(counter::set);
    }

    @EventListener
    protected void onAfterCreateTaco(TacoCreationEvent event) {
        counter.incrementAndGet();
    }

    @ManagedAttribute
    public long getCounter() {
        return counter.get();
    }

    @ManagedOperation
    public long incrementCounter(long delta) {
        long before = counter.get();
        long after = counter.addAndGet(delta);
        if ((after / 100) > (before / 100)) {
            final Notification notification = new Notification(
                    "taco.count", this,
                    before, after + "th taco created!"
            );
            notification.setUserData("Number of tacos created");
            publisher.sendNotification(notification);
        }

        return after;
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher notificationPublisher) {
        this.publisher = notificationPublisher;
    }
}
