package temesgen.girmay.feature_matrix_game.domain.use_case;

import temesgen.girmay.feature_matrix_game.configuration.Config;

import java.math.BigDecimal;
import java.util.Map;

import static java.math.BigDecimal.ZERO;

public class BasicRewardCalculationStrategy implements RewardCalculationStrategy {
    private final Config config;

    public BasicRewardCalculationStrategy(Config config) {
        this.config = config;
    }

    @Override
    public BigDecimal calculateReward(Map<String, BigDecimal> rewardMultipliers, BigDecimal bet) {
        BigDecimal rewardMultiplier = rewardMultipliers.entrySet().stream()
                .map(e -> config.symbols().get(e.getKey()).rewardMultiplier().multiply(e.getValue()))
                .reduce(BigDecimal::add)
                .orElse(ZERO);

        assert rewardMultiplier.signum() > 0;

        return bet.multiply(rewardMultiplier);
    }
}
