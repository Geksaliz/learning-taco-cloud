package tacos.configuration;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import tacos.persistence.repository.TacoRepository;

import java.util.Map;

@Component
public class TacoCountInfoContributor implements InfoContributor {
    private final TacoRepository tacoRepository;

    public TacoCountInfoContributor(TacoRepository tacoRepository) {
        this.tacoRepository = tacoRepository;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail(
                "taco-stats",
                Map.of("count", tacoRepository.count().block())
        );
    }
}
