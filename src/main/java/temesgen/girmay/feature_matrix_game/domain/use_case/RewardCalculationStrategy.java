package temesgen.girmay.feature_matrix_game.domain.use_case;

import java.math.BigDecimal;
import java.util.Map;

public interface RewardCalculationStrategy {
    BigDecimal calculateReward(Map<String, BigDecimal> rewardMultipliers, BigDecimal bet);
}
